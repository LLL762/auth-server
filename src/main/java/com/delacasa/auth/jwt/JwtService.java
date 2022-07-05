package com.delacasa.auth.jwt;

public interface JwtService<T> {

	String createToken(String subject);

	T readToken(String token);

}
