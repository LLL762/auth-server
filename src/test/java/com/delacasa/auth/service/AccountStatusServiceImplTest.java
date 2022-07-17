package com.delacasa.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import com.delacasa.auth.config.AccountConfig;
import com.delacasa.auth.entity.AccountStatus;
import com.delacasa.auth.repo.AccountStatusRepo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.read.ListAppender;

@ExtendWith(MockitoExtension.class)
class AccountStatusServiceImplTest {

	// TODO choose a sl4j impl and test logging.

	private final Logger logger = (Logger) LoggerFactory.getLogger(AccountStatusServiceImpl.class);
	private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

	// add the appender to the logger

	@Mock
	private Appender<AccountStatusServiceImpl> mockedAppender;

	@Mock
	private AccountStatusRepo repo;

	@Mock
	private AccountConfig config;

	@InjectMocks
	private AccountStatusServiceImpl service;

	@BeforeEach
	private void initLogList() {

		listAppender.start();
		logger.addAppender(listAppender);

	}

	@AfterEach
	private void clearLogList() {

		listAppender.clearAllFilters();

	}

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
		final List<ILoggingEvent> appenderList = listAppender.list;

		when(repo.findAll()).thenReturn(mockDbStatus);
		when(config.getStatusNames()).thenReturn(mockConfigNames);

		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> service.getStatusMap())
				.withMessage(expectedMessage);

		assertThat(appenderList.get(0).getMessage()).isEqualTo(expectedMessage);
		assertThat(appenderList.get(0).getLevel()).isEqualTo(Level.ERROR);

	}

	@Test
	void dbDoesNotContainTwoStatus() {

		final List<AccountStatus> mockDbStatus = List.of(
				new AccountStatus(12, "Riri"),
				new AccountStatus(-427, "Fifi"),
				new AccountStatus(0, "Loulou"));

		final String[] mockConfigNames = { "Riri", "Fifi", "Picksou", "Loulou", "Donald" };
		final String expectedMessage = "Account status : [Picksou, Donald] not found !";
		final List<ILoggingEvent> appenderList = listAppender.list;

		when(repo.findAll()).thenReturn(mockDbStatus);
		when(config.getStatusNames()).thenReturn(mockConfigNames);

		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> service.getStatusMap())
				.withMessage(expectedMessage);

		assertThat(appenderList.get(0).getMessage()).isEqualTo(expectedMessage);
		assertThat(appenderList.get(0).getLevel()).isEqualTo(Level.ERROR);

	}

	@Test
	void configDoesNotContainTwoStatus() {

		final List<AccountStatus> mockDbStatus = List.of(
				new AccountStatus(12, "Riri"),
				new AccountStatus(-427, "Fifi"),
				new AccountStatus(-427, "PickSou"),
				new AccountStatus(10, "Loulou"),
				new AccountStatus(1658, "Donald"));

		final String[] mockConfigNames = { "Riri", "Fifi", "Loulou" };
		final List<ILoggingEvent> appenderList = listAppender.list;

		when(repo.findAll()).thenReturn(mockDbStatus);
		when(config.getStatusNames()).thenReturn(mockConfigNames);

		assertThat(service.getByName("Riri")).contains(new AccountStatus(12, "Riri"));
		assertThat(service.getByName("Robert")).isEmpty();
		assertThat(appenderList).hasSize(2);

		assertThat(appenderList.get(0).getMessage()).isEqualTo("PickSou is an unsuported account status");
		assertThat(appenderList.get(0).getLevel()).isEqualTo(Level.WARN);

		assertThat(appenderList.get(1).getMessage()).isEqualTo("Donald is an unsuported account status");
		assertThat(appenderList.get(1).getLevel()).isEqualTo(Level.WARN);

	}

}
