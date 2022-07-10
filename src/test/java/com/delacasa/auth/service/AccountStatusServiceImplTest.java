package com.delacasa.auth.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountStatusServiceImplTest {

	@Autowired
	private AccountStatusServiceImpl service;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void test() {

		System.out.println(service.getAllStatus());

	}

}
