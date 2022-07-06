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

import com.delacasa.auth.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		System.out.println(request.getContentLength());

		return authenticationManager.authenticate(new CustomAuth(new AuthRequestDetails(request)));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		response.setHeader(SPRING_SECURITY_FORM_USERNAME_KEY, SPRING_SECURITY_FORM_PASSWORD_KEY);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.unsuccessfulAuthentication(request, response, failed);
	}

}
