package com.delacasa.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.entity.AccountStatus;

@SpringBootTest
public class ITAccountStatusServiceImplTest {

	@Autowired
	private AccountStatusServiceImpl service;

	@Autowired
	private AccountService accountService;

	@Nested
	class ConfigAndDbMatches {

		@Test
		void testGetAnExistingStatus() {

			final String statusName = "LOCKED_ADMIN";
			final AccountStatus expectedResult = new AccountStatus(3, "LOCKED_ADMIN");

			assertThat(service.getByName(statusName)).contains(expectedResult);

		}

		@Test
		void testGetAnNotExistingStatus() {

			final String statusName = "HALF_GOD";

			assertThat(service.getByName(statusName)).isEmpty();

		}

		@Test
		void getStatusMap() {

			final Map<String, AccountStatus> expectedResult = Map.of(
					"OK", new AccountStatus(1, "OK"),
					"LOCKED_AUTH", new AccountStatus(2, "LOCKED_AUTH"),
					"LOCKED_ADMIN", new AccountStatus(3, "LOCKED_ADMIN"),
					"BANNED", new AccountStatus(4, "BANNED"));

			assertThat(service.getStatusMap()).isEqualTo(expectedResult);

		}

		@Test
		void equality() {

			Account arnold = accountService.getAccountById(1L).get();

			assertThat(service.getByName("OK")).contains(arnold.getStatus());

			arnold.setStatus(service.getByName("LOCKED_AUTH").orElseThrow());
			accountService.save(arnold);

			assertThat(service.getByName("LOCKED_AUTH")).contains(arnold.getStatus());

		}

	}

}
