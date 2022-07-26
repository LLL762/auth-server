package com.delacasa.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties("com.delacasa.auth.login")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class AppLoginConfig {

	private final String url;
	private final String successUrl;
	private final String twoFAuthRequiredUrl;
	private final String mailRegex;
	private final int maxTries;

}
