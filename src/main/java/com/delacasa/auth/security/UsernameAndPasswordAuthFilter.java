package com.delacasa.auth.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		// TODO Auto-generated method stub
		return super.attemptAuthentication(request, response);
	}

	@Override
	public void setPasswordParameter(String passwordParameter) {
		// TODO Auto-generated method stub
		super.setPasswordParameter(passwordParameter);
	}

}
