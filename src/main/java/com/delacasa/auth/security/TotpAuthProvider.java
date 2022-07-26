package com.delacasa.auth.security;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.delacasa.auth.config.AccStatusConfig;
import com.delacasa.auth.entity.Account;
import com.delacasa.auth.service.AccountService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TotpAuthProvider implements AuthenticationProvider {

	private final AccountService accountService;
	private final CustomAuthService customAuthService;
	private final PasswordEncoder passwordEncoder;
	private final AccStatusConfig statusConfig;

	@Override
	@Transactional
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		final TotpAuth auth = (TotpAuth) authentication;
		final Account account = accountService.getAccountById(Long.valueOf(auth.getPrincipal()))
				.orElseThrow(() -> new BadCredentialsException("id not found"));

		checkStatus(account);
		checkTotp(auth.getCredentials(), account);

		customAuthService.setUp(auth, account);

		cleanAccount(account);

		accountService.save(account);

		return auth;
	}

	private void cleanAccount(final Account account) {

		account.setStatus(statusConfig.getOk());
		account.setTotp(null);
		account.setTotpExpiration(null);
		account.setTotpIp(null);
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(TotpAuth.class);
	}

	private void checkStatus(final Account account) {

		if (!account.getStatus().equals(statusConfig.getLockedAuth())) {

			throw new DisabledException("wrong status");

		}

	}

	private void checkTotp(final String totp, final Account account) {

		if (account.getTotpExpiration().isBefore(LocalDateTime.now())) {

			throw new CredentialsExpiredException("expired!!");
		}

		if (!passwordEncoder.matches(totp, account.getTotp())) {

			throw new BadCredentialsException("Invalid totp");

		}

	}

}
