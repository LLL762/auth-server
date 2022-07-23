package com.delacasa.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.delacasa.auth.config.AccountConfig;
import com.delacasa.auth.config.AppLoginConfig;
import com.delacasa.auth.mail.EmailTotpConfig;

@SpringBootApplication
@EnableConfigurationProperties({ AccountConfig.class, EmailTotpConfig.class, AppLoginConfig.class })
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
