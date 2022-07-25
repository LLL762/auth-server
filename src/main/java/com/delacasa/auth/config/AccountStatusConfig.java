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
public class AccountStatusConfig {

	private final Map<String, String> status = new HashMap<>();

}
