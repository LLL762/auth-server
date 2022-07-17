package com.delacasa.auth.jwt;

import com.delacasa.auth.security.CustomAuth;

public interface JwtService<T> {

	String createToken(CustomAuth auth);

	String createRefreshToken(CustomAuth auth);

	T readToken(String token);

}
