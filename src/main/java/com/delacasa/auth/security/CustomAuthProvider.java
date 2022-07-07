package com.delacasa.auth.security;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.model.AccountStatusEnum;
import com.delacasa.auth.service.AccountService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

	private final PasswordEncoder encoder;
	private final AccountService accountService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final CustomAuth auth = (CustomAuth) authentication;
		final Account account = accountService.getAccountByUsernameOrMail(auth.getCredentials()).orElseThrow();

		checkStatus(account);
		checkPassword(auth.getCredentials(), account);

		return auth;
	}

	@Override
	public boolean supports(final Class<?> authentication) {

		return authentication.equals(CustomAuth.class);
	}

	private void checkStatus(final Account account) throws DisabledException {

		switch (AccountStatusEnum.valueOf(account.getStatus().getName())) {

			case OK -> {

			}
			case BANNED -> {
				throw new DisabledException("Account banned");
			}
			case LOCKED_AUTH -> {
				// TO DO
			}
			case LOCKED_ADMIN -> {

				// TO DO

			}

			default -> throw new IllegalArgumentException();
		}

	}

	private void checkPassword(final String password, final Account account) {

		if (!encoder.encode(password).equals(account.getPassword())) {

			throw new BadCredentialsException("Invalid password");

		}

	}

}
