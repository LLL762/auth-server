package com.delacasa.auth.config;

import static lombok.AccessLevel.PACKAGE;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "com.delacasa.auth.login")
@Getter
@Setter(value = PACKAGE)
public class AppLoginConfig {

	private String url;
	private String successUrl;
	private String mailRegex;

}
