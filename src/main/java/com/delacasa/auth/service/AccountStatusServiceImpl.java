package com.delacasa.auth.service;

import static java.util.Collections.unmodifiableList;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.delacasa.auth.entity.AccountStatus;
import com.delacasa.auth.repo.AccountStatusRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountStatusServiceImpl {

	private AccountStatusRepo repo;

	private boolean hasInit = false;

	private List<AccountStatus> allStatus;

	private final List<String> names = List.of("OK", "LOCKED_AUTH", "LOCKED_ADMIN", "BANNED");

	private byte[] indexes = new byte[names.size()];

	private byte okIndex = -1;
	private byte lockAuthIndex = -1;
	private byte lockAdminIndex = -1;
	private byte bannedIndex = -1;

	public AccountStatusServiceImpl(AccountStatusRepo repo) {

		this.repo = repo;

	}

	private synchronized void init() {

		byte index = 0;

		if (hasInit) {
			return;
		}

		allStatus = unmodifiableList(repo.findAll());

		final Map<String, AccountStatus> collect = allStatus.parallelStream().filter(s -> names.contains(s.getName()))

															.collect(Collectors.toUnmodifiableMap(
																	AccountStatus::getName, s -> s));

		names.containsAll(collect.keySet());

		System.out.println(collect);

		this.repo = null;

		for (final AccountStatus status : allStatus) {

			mapIndexes(status, index);
			index++;
		}

		checkIndexes();

		hasInit = true;

	}

	private void checkInit() {

		if (!hasInit) {
			init();
		}

	}

	public List<AccountStatus> getAllStatus() {

		checkInit();
		return allStatus;
	}

	private void mapIndexes(final AccountStatus status, final byte index) {

//		final int i = names.indexOf(status.getName());
//
//		if (i == -1) {
//			log.warn("unsupported status : " + status.getName());
//			return;
//
//		}
//
//		indexes[i] = index;

		switch (status.getName()) {
		case "OK": {
			okIndex = index;
			break;
		}
		case "LOCKED_AUTH": {
			lockAuthIndex = index;
			break;
		}
		case "LOCKED_ADMIN": {
			lockAdminIndex = index;
			break;
		}
		case "BANNED": {
			bannedIndex = index;
			break;
		}

		default: {
			log.warn("unsupported status : " + status.getName());
			break;
		}

		}

	}

	private void checkIndexes() {

		final StringBuilder msg = new StringBuilder();

		msg.append(okIndex == -1 ? "OK status is missing+/n" : "");
		msg.append(lockAuthIndex == -1 ? "LOCKED_AUTH status is missing+/n" : "");
		msg.append(lockAdminIndex == -1 ? "LOCKED_ADMIN is missing+/n" : "");
		msg.append(bannedIndex == -1 ? "BANNED status is missing+/n" : "");

		if (!msg.isEmpty()) {
			throw new NoSuchElementException(msg.toString());
		}

	}

}
