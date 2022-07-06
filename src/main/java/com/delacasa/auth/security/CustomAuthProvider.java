package com.delacasa.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

	private final PasswordEncoder encoder;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final CustomAuth auth = (CustomAuth) authentication;

		System.out.println("CustomAuthProvider");

		return auth;
	}

	@Override
	public boolean supports(final Class<?> authentication) {

		return authentication.equals(CustomAuth.class);
	}

}
