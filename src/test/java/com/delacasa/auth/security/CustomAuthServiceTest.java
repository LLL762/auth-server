package com.delacasa.auth.security;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.delacasa.auth.config.AccountConfig;
import com.delacasa.auth.jwt.JwtConfig;

@ExtendWith(MockitoExtension.class)
class CustomAuthServiceTest {

	@Mock
	private AccountConfig accountConfig;
	@Mock
	private JwtConfig jwtConfig;
	@InjectMocks
	private CustomAuthService authService;

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
