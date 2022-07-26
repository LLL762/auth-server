package com.delacasa.auth.config;

import static lombok.AccessLevel.NONE;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;

import com.delacasa.auth.entity.AccountStatus;
import com.delacasa.auth.repo.AccountStatusRepo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Getter
@Slf4j
public class AccStatusConfig {

//Beware of circular dependencies between repositories and configuration properties beans.

	@Getter(value = NONE)
	private final AccountStatusRepo repo;
	@Getter(value = NONE)
	private final AccountConfig config;

	private boolean hasInit = false;

	private List<AccountStatus> dbStatus;

	private AccountStatus ok;
	private AccountStatus lockedAuth;
	private AccountStatus lockedAdmin;
	private AccountStatus banned;

	public synchronized void init() {

		dbStatus = (List<AccountStatus>) repo.findAll();

		ok = convert(config.getStatus().get("ok"));
		lockedAuth = convert(config.getStatus().get("locked-auth"));
		lockedAdmin = convert(config.getStatus().get("locked-admin"));
		banned = convert(config.getStatus().get("banned"));

		hasInit = true;

	}

	public void check() {

		final List<String> dbStatusNames = dbStatus.stream()
				.map(AccountStatus::getName)
				.collect(Collectors.toList());
		String msg;

		for (final String statusName : config.getStatus().values()) {

			if (!dbStatusNames.remove(statusName)) {

				throw new NoSuchElementException();

			}

		}

		if (!dbStatusNames.isEmpty()) {

			msg = dbStatusNames.stream().collect(Collectors.joining(","));
			log.warn("Following account status are unsupported but present in storage : " + msg);

		}

	}

	private AccountStatus convert(final String statusName) {

		return dbStatus.stream().filter(s -> s.getName().equalsIgnoreCase(statusName.trim()))
				.findFirst()
				.orElseThrow();

	}

	public List<AccountStatus> getDbStatus() {

		return new ArrayList<>(dbStatus);
	}

}
