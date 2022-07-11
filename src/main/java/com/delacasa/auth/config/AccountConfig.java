package com.delacasa.auth.config;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.RequiredArgsConstructor;

@ConfigurationProperties("com.delacasa.auth.account")
@ConstructorBinding
@RequiredArgsConstructor
public class AccountConfig {

	private final String[] statusNames;

	public String[] getStatusNames() {
		return Arrays.copyOf(statusNames, statusNames.length);
	}

}
