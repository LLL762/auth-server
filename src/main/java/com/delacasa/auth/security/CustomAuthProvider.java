package com.delacasa.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.service.AccountService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

	private final PasswordEncoder encoder;
	private final AccountService accountService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final CustomAuth auth = (CustomAuth) authentication;
		final Account account = accountService.getAccountByUsernameOrMail(auth.getCredentials()).orElseThrow();

		return auth;
	}

	@Override
	public boolean supports(final Class<?> authentication) {

		return authentication.equals(CustomAuth.class);
	}

	private boolean checkStatus(final Account account) {

		return account.getAccountStatus().getName().equals("Robert");

	}

	private boolean checkremainingTries(final Account account) {

		return account.getAccountStatus().getName().equals("Robert");

	}

	private boolean checkPassword(final String password, final Account account) {

		return encoder.encode(password).equals(account.getPassword());

	}

}
