package com.delacasa.auth.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountStatusConfigTest {

	@Autowired
	private AccountStatusConfig config;

	@Test
	void test() {
		System.out.println(config.getStatus());
	}

}
