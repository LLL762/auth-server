package com.delacasa.auth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.delacasa.auth.config.AppLoginConfig;
import com.delacasa.auth.jwt.JwtConfig;
import com.delacasa.auth.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final CustomAuthService customAuthService;
	private final JwtService<?> jwtService;
	private final JwtConfig jwtConfig;
	private final AppLoginConfig loginConfig;

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

		response.setHeader(jwtConfig.getAccessTokenHeader(), jwtConfig.getPrefix() + jwtService.createToken(auth));
		response.setHeader(jwtConfig.getRefreshTokenHeader(), jwtService.createRefreshToken(auth));

		response.sendRedirect(loginConfig.getSuccessUrl());

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		super.unsuccessfulAuthentication(request, response, failed);
	}

}
