package com.delacasa.auth.config;

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

	private final Map<String, String> status;
	private final Map<String, String> roles;

}
