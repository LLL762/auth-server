package com.delacasa.auth.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccStatusConfigTest {

	@Autowired
	private AccStatusConfig config;

	@Test
	void test() {

		config.init();
		System.out.println(config.getBanned());

	}

}
