package com.delacasa.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;

import com.delacasa.auth.config.AccountConfig;
import com.delacasa.auth.entity.AccountStatus;
import com.delacasa.auth.repo.AccountStatusRepo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
class AccountStatusServiceImplTest {

	@Mock
	private Logger logger;

	@Mock
	private AccountStatusRepo repo;

	@Mock
	private AccountConfig config;

	@InjectMocks
	private AccountStatusServiceImpl service;

	@Nested
	class ConfigAndDbMatches {

		private final List<AccountStatus> mockDbStatus = List.of(
				new AccountStatus(12, "Riri"),
				new AccountStatus(-427, "Fifi"),
				new AccountStatus(0, "Loulou"));

		private final String[] mockConfigNames = { "Riri", "Fifi", "Loulou" };

		private void verifyMethodsCalls() {

			verify(repo, times(1)).findAll();
			verify(config, times(1)).getStatusNames();
			verify(logger, never()).warn(any());
			verify(logger, never()).error(any());

		}

		@Test
		void testGetAnExistingStatus() {

			final AccountStatus expectedResult = new AccountStatus(-427, "Fifi");

			when(repo.findAll()).thenReturn(mockDbStatus);
			when(config.getStatusNames()).thenReturn(mockConfigNames);

			assertThat(service.getByName("Fifi")).contains(expectedResult);

			verifyMethodsCalls();

		}

		@Test
		void testGetANoExistingStatus() {

			when(repo.findAll()).thenReturn(mockDbStatus);
			when(config.getStatusNames()).thenReturn(mockConfigNames);

			assertThat(service.getByName("Robert")).isEmpty();

			verifyMethodsCalls();

		}

		@Test
		void testInitOnlyCalledOnce() {

			when(repo.findAll()).thenReturn(mockDbStatus);
			when(config.getStatusNames()).thenReturn(mockConfigNames);

			service.getByName("Riri");
			service.getStatusMap();
			service.getByName("Rebecca");

			verifyMethodsCalls();
		}

	}

	@Test
	void dbDoNotContainOneStatus() {

		final List<AccountStatus> mockDbStatus = List.of(
				new AccountStatus(12, "Riri"),
				new AccountStatus(-427, "Fifi"),
				new AccountStatus(0, "Loulou"));

		final String[] mockConfigNames = { "Riri", "Fifi", "Loulou", "Donald" };
		final String expectedMessage = "Account status : [Donald] not found !";

		when(repo.findAll()).thenReturn(mockDbStatus);
		when(config.getStatusNames()).thenReturn(mockConfigNames);

		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> service.getStatusMap())
				.withMessage(expectedMessage);

		verify(logger, times(1)).error(any());

	}

}
