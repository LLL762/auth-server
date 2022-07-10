package com.delacasa.auth.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.delacasa.auth.config.AppLoginConfig;
import com.delacasa.auth.jwt.JwtService;
import com.delacasa.auth.service.AccountService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final AppLoginConfig loginConfig;
	private final AuthenticationConfiguration authConfig;
	private final AccountService accountService;
	private final JwtService<?> jwtService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(STATELESS)
			.and()

			.formLogin()
			.loginProcessingUrl(loginConfig.getUrl())
			.defaultSuccessUrl(loginConfig.getSuccessUrl())

			.and()

			.authenticationManager(authManager())
			.addFilter(new UsernameAndPasswordAuthFilter(authManager(), jwtService))

		;

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CustomAuthProvider authProvider() {
		return new CustomAuthProvider(passwordEncoder(), accountService, loginConfig);

	}

	@Bean
	AuthenticationManager authManager()
			throws Exception {

		return authConfig.getAuthenticationManager();
	}

}
