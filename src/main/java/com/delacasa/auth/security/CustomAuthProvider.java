package com.delacasa.auth.security;

import javax.transaction.Transactional;

import com.delacasa.auth.config.AppLoginConfig;
import com.delacasa.auth.entity.Account;
import com.delacasa.auth.service.AccountService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomAuthProvider implements AuthenticationProvider {

	private final PasswordEncoder encoder;
	private final AccountService accountService;
	private final CustomAuthService customAuthService;
	private final AppLoginConfig loginConfig;

	@Override
	@Transactional
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final CustomAuth auth = (CustomAuth) authentication;

		final Account account = accountService.getAccountByUsernameOrMail(auth.getName())
				.orElseThrow(() -> new BadCredentialsException("Username not found"));

		checkStatus(account);
		checkPassword(auth.getCredentials(), account);

		customAuthService.setUp(auth, account);

		if (account.isTwoFactorAuth()) {

		}

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
				// TO DO
			}
			case "LOCKED_ADMIN" -> {

				// TO DO

			}

			default -> throw new IllegalArgumentException();
		}

	}

	private void checkPassword(final String password, final Account account) {

		if (!encoder.matches(password, account.getPassword())) {

			throw new BadCredentialsException("Invalid password");
		}
	}

	private void checkFailedAttempt(final String password, final Account account) {

		if (account.getFailedAttempt() > loginConfig.getMaxTries()) {

			throw new BadCredentialsException("Invalid password");
		}
	}

}
