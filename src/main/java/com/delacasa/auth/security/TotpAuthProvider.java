package com.delacasa.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.delacasa.auth.service.AccountService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TotpAuthProvider implements AuthenticationProvider {

	private final AccountService accountService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		final TotpAuth auth = (TotpAuth) authentication;

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(TotpAuth.class);
	}

}
