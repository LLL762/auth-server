package com.delacasa.auth.security;

import static com.delacasa.auth.utility.IPAddressUtility.getOriginIPAddress;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;

@Getter
public class AuthRequestDetails {

	private final String usernameOrEmail;
	private final String password;
	private String userIpAddress;
	private String rememberMe;

	public AuthRequestDetails(final HttpServletRequest request) {

		usernameOrEmail = request.getParameter("username");
		password = request.getParameter("password");
		userIpAddress = getOriginIPAddress(request);
		rememberMe = request.getParameter("remember-me");

	}

}
