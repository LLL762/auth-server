package com.delacasa.auth.jwt;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtClaimsModel {

	private final String subject;
	private final String ipAdress;
	private final String role;
	private final Set<String> authorizations;
	private final Set<String> restrictions;

}
