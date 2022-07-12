package com.delacasa.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "com.delacasa.auth.jwt")
@Getter
@Setter
public class JwtConfig {

	private String issuer;
	private String audience;
	private String prefix;
	private int expirationInSeconds;
	private int refreshExpirationInSeconds;

	private String claimIp;
	private String claimAuthorities;

}
