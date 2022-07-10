package com.delacasa.auth.utility;

import javax.servlet.http.HttpServletRequest;

public final class IPAddressUtility {

	private IPAddressUtility() {

	}

	public static String getOriginIPAddress(final HttpServletRequest request) {

		return request.getRemoteAddr();
	}

}
