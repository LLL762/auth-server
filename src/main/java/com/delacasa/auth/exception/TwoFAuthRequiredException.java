package com.delacasa.auth.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

public class TwoFAuthRequiredException extends AuthenticationException {

	@Getter
	private static final String DEFAULT_MSG = "Credentials are ok, but another step is required to be authenticated";

	public TwoFAuthRequiredException() {
		super(DEFAULT_MSG);

	}

	public TwoFAuthRequiredException(String msg) {
		super(msg);

	}

	public TwoFAuthRequiredException(String msg, Throwable cause) {
		super(msg, cause);

	}

}
