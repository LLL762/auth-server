package com.delacasa.auth.exception;

public class JwtTokenException extends RuntimeException {

	public JwtTokenException() {
		super();

	}

	public JwtTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public JwtTokenException(String message, Throwable cause) {
		super(message, cause);

	}

	public JwtTokenException(String message) {
		super(message);

	}

	public JwtTokenException(Throwable cause) {
		super(cause);

	}

}
