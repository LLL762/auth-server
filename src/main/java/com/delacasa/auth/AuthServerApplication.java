package com.delacasa.auth;

import com.delacasa.auth.config.AccountConfig;
import com.delacasa.auth.mail.EmailTotpConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ AccountConfig.class, EmailTotpConfig.class })
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
