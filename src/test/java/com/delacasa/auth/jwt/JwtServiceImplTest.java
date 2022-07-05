package com.delacasa.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.delacasa.auth.config.JwtConfig;
import com.nimbusds.jwt.SignedJWT;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")

class JwtServiceImplTest {

	@MockBean
	private JwtConfig config;

	@Autowired
	@InjectMocks
	private JwtServiceImpl service;

	@Test
	void allGood() throws Exception {

		final String subject = "Arnold";
		final String expectedIssuer = "Alice";
		final String jwe;
		final SignedJWT jws;

		when(config.getIssuer()).thenReturn(expectedIssuer);

		jwe = service.createToken(subject);
		jws = service.readToken(jwe);

		assertThat(jws.getJWTClaimsSet().getSubject()).isEqualTo(subject);
		assertThat(jws.getJWTClaimsSet().getIssuer()).isEqualTo(expectedIssuer);

	}

}
