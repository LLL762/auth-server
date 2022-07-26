package com.delacasa.auth.security;

import javax.mail.MessagingException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.delacasa.auth.config.AccRoleConfig;
import com.delacasa.auth.config.AccStatusConfig;
import com.delacasa.auth.config.AppLoginConfig;
import com.delacasa.auth.entity.Account;
import com.delacasa.auth.exception.TwoFAuthRequiredException;
import com.delacasa.auth.mail.TotpEmailService;
import com.delacasa.auth.service.AccountService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomAuthProvider implements AuthenticationProvider {

	private final PasswordEncoder encoder;
	private final AccountService accountService;
	private final CustomAuthService customAuthService;
	private final AppLoginConfig loginConfig;
	private final AccStatusConfig statusConfig;
	private final TotpEmailService totpEmailService;
	private final AccRoleConfig roleConfig;

	@Override
	@Transactional(noRollbackFor = TwoFAuthRequiredException.class)
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final CustomAuth auth = (CustomAuth) authentication;
		final Account account = accountService.getAccountByUsernameOrMail(auth.getName())
				.orElseThrow(() -> new BadCredentialsException("Username not found"));

		checkStatus(account);
		checkPassword(auth.getCredentials(), account);

		customAuthService.setUp(auth, account);

		if (shouldUseTwoFactorAuth(account)) {

			account.setStatus(statusConfig.getLockedAuth());
			account.setFailedAttempt((byte) 0);

			try {
				totpEmailService.sendCode(account, auth);
			} catch (MessagingException e) {
				// TODO better handling.
				throw new RuntimeException();
			}

			accountService.save(account);
			throw new TwoFAuthRequiredException();

		}
		accountService.save(account);
		return auth;
	}

	@Override
	public boolean supports(final Class<?> authentication) {

		return authentication.equals(CustomAuth.class);
	}

	private void checkStatus(final Account account) throws DisabledException {

		switch (account.getStatus().getName()) {

		case "OK" -> {

		}
		case "BANNED" -> {
			throw new DisabledException("Account banned");
		}
		case "LOCKED_AUTH" -> {

		}
		case "LOCKED_ADMIN" -> {

			throw new LockedException("Account has been locked by an admin");

		}

		default -> throw new IllegalArgumentException();
		}

	}

	private boolean shouldUseTwoFactorAuth(final Account account) {
		return account.isTwoFactorAuth() ||
				account.getStatus().equals(statusConfig.getLockedAuth()) ||
				account.getRole().getAccessLevel() >= roleConfig.getModerator().getAccessLevel();

	}

	private void checkPassword(final String password, final Account account) {

		if (!encoder.matches(password, account.getPassword())) {

			throw new BadCredentialsException("Invalid password");
		}
	}

	private void checkFailedAttempt(final Account account) {

		if (account.getFailedAttempt() > loginConfig.getMaxTries()) {

			throw new BadCredentialsException("Invalid password");
		}
	}

	private void failAuth(final Account account, final AuthenticationException authException) {

		final int fail = account.getFailedAttempt() + 1;

		account.setFailedAttempt(fail);

		if (fail > loginConfig.getMaxTries()) {

			account.setStatus(statusConfig.getLockedAdmin());
			account.setFailedAttempt(0);
			throw new LockedException("account is locked !");

		}

		accountService.save(account);

		throw authException;

	}

}
