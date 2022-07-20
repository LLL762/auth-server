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
import com.delacasa.auth.jwt.JwtConfig;
import com.delacasa.auth.jwt.JwtService;
import com.delacasa.auth.mail.EmailTotpConfig;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final AppLoginConfig loginConfig;
	private final AuthenticationConfiguration authConfig;
	private final EmailTotpConfig totpConfig;
	private final CustomAuthService customAuthService;
	private final JwtService<?> jwtService;
	private final JwtConfig jwtConfig;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http

				.sessionManagement().sessionCreationPolicy(STATELESS)

				.and()
				.authorizeHttpRequests()
				.antMatchers(totpConfig.getTotpUrl() + "/**").permitAll()

				.and()
				.formLogin()
				.loginProcessingUrl(loginConfig.getUrl())
				.defaultSuccessUrl(loginConfig.getSuccessUrl())

				.and()

				.authenticationManager(authManager())
				.addFilter(new UsernameAndPasswordAuthFilter(authManager(), customAuthService, jwtService, jwtConfig,
						loginConfig));

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authManager()
			throws Exception {

		return authConfig.getAuthenticationManager();
	}

}
