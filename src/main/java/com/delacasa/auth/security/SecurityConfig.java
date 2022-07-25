package com.delacasa.auth.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
	private final EmailTotpConfig totpConfig;
	private final CustomAuthService customAuthService;
	private final JwtService<?> jwtService;
	private final JwtConfig jwtConfig;
	private final CustomAuthProvider authProvider;
	private final TotpAuthProvider totpAuthProvider;

	private TotpFilter generateTotpFilter() throws Exception {

		final TotpFilter output = new TotpFilter(authManager(), customAuthService, jwtService, jwtConfig, loginConfig);
		output.setFilterProcessesUrl(totpConfig.getTotpUrl() + "/**");

		return output;

	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.sessionManagement().sessionCreationPolicy(STATELESS)

				.and()

				.authorizeHttpRequests()
				.antMatchers(totpConfig.getTotpUrl() + "/**", jwtConfig.getRefreshTokenUrl() + "/**")
				.permitAll()

				.and()

				.formLogin()
				.loginProcessingUrl(loginConfig.getUrl())
				.defaultSuccessUrl(loginConfig.getSuccessUrl())

				.and()

				.authenticationManager(authManager())
				.addFilter(new UsernameAndPasswordAuthFilter(authManager(), customAuthService, jwtService, jwtConfig,
						loginConfig))
				.addFilter(generateTotpFilter())

		;

		return http.build();
	}

	@Bean
	ProviderManager authManager() {

		return new ProviderManager(authProvider, totpAuthProvider);
	}

}
