package com.delacasa.auth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@Profile(value = { "dev", "test" })
public class SecurityConfigDev {

	@Value("${spring.h2.console.path}")
	private String h2ConsolePath;

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().antMatchers(h2ConsolePath + "/**");
	}

}
