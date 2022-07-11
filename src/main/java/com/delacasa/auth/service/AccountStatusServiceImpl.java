package com.delacasa.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.delacasa.auth.config.AccountConfig;
import com.delacasa.auth.entity.AccountStatus;
import com.delacasa.auth.repo.AccountStatusRepo;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountStatusServiceImpl implements AccountStatusService {

	private AccountStatusRepo repo;
	private final AccountConfig config;

	private boolean hasInit = false;

	private Map<String, AccountStatus> statusMap = new HashMap<>();

	public AccountStatusServiceImpl(AccountStatusRepo repo, final AccountConfig config) {

		this.repo = repo;
		this.config = config;

	}

	private synchronized void init() {

		final List<String> names = new ArrayList<>(Arrays.asList(config.getStatusNames()));
		String errorMsg;

		if (hasInit) {
			return;
		}

		for (final AccountStatus status : repo.findAll()) {

			final String name = status.getName();
			final int index = names.indexOf(name);

			if (index >= 0) {

				statusMap.put(name, status);
				names.remove(index);

			} else {

				log.warn(name + " is an unsuported account status");

			}

		}

		if (!names.isEmpty()) {

			errorMsg = "Account status : " + names.toString() + " not found !";
			log.error(errorMsg);
			throw new NoSuchElementException(errorMsg);

		}

		this.repo = null;
		hasInit = true;

	}

	private void checkInit() {

		if (!hasInit) {
			init();
		}

	}

	@Override
	public Optional<AccountStatus> getByName(final String name) {

		checkInit();

		final AccountStatus status = statusMap.get(name);

		return status == null ? Optional.empty() : Optional.of(status);

	}

	public Map<String, AccountStatus> getStatusMap() {

		checkInit();
		return new HashMap<>(statusMap);
	}

}
