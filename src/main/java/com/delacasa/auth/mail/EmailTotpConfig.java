package com.delacasa.auth.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties("com.delacasa.auth.mail")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class EmailTotpConfig {

	private final String sender;
	private final String contentTemplateUrl;
	private final String subject;

}
