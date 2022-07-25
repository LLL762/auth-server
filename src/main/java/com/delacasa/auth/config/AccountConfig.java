package com.delacasa.auth.config;

import java.util.Arrays;
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

	private final String[] statusNames;
	private final String[] rolesNames;
	private final String rolesPrefix;
	private final String categoryAuthorityPrefix;
	private final String categoryRestrictionPrefix;
	private final Map<String, String> status = new HashMap<>();

	private final String statusOk;
	private final String statusLockedAuth;
	private final String statusLockedAdmin;
	private final String statusBanned;

	public String[] getStatusNames() {
		return Arrays.copyOf(statusNames, statusNames.length);
	}

	public String[] getrolesNames() {
		return Arrays.copyOf(rolesNames, rolesNames.length);
	}

}
