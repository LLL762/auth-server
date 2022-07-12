package com.delacasa.auth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delacasa.auth.jwt.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final CustomAuthService customAuthService;
	private final JwtService jwtService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		final CustomAuth auth = new CustomAuth();
		customAuthService.initAuth(auth, new AuthRequestDetails(request));

		return authenticationManager.authenticate(auth);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		final CustomAuth auth = (CustomAuth) authResult;

		response.addHeader("Authorization", jwtService.createToken(auth));

		super.successfulAuthentication(request, response, chain, authResult);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		super.unsuccessfulAuthentication(request, response, failed);
	}

}
