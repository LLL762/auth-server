package com.delacasa.auth.security;

import static com.delacasa.auth.utility.IPAddressUtility.getOriginIPAddress;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;

@Getter
public class TotpRequestDetails {

	private final String id;
	private final String totp;
	private String userIpAddress;
	private String rememberMe;

	public TotpRequestDetails(final HttpServletRequest request) {

		id = getLastPart(request.getServletPath());
		totp = request.getParameter("totp");
		userIpAddress = getOriginIPAddress(request);
		rememberMe = request.getParameter("remember-me");

	}

	private String getLastPart(final String url) {

		final int index = url.lastIndexOf("/");

		return index != -1 ? url.substring(index + 1) : url;

	}

}
