package com.delacasa.auth.jwt;

public interface JwtService<T> {

	String createToken(JwtClaimsModel claimsModel);

	T readToken(String token);

}
