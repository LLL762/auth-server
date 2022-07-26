package com.delacasa.auth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties("com.delacasa.auth.account")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class AccountConfig {

	private final String rolesPrefix;
	private final String categoryAuthorityPrefix;
	private final String categoryRestrictionPrefix;
	private final Map<String, String> status = new HashMap<>();
	private final Map<String, String> role = new HashMap<>();

	private final String statusOk;
	private final String statusLockedAuth;
	private final String statusLockedAdmin;
	private final String statusBanned;

	private final String roleFree;
	private final String rolePremium;
	private final String roleModerator;
	private final String roleAdmin;
	private final String roleGod;

}
