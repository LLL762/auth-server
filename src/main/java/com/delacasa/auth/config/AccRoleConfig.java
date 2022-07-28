package com.delacasa.auth.config;

import static lombok.AccessLevel.NONE;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;

import com.delacasa.auth.entity.AccountRole;
import com.delacasa.auth.repo.AccountRoleRepo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Getter
@Slf4j
public class AccRoleConfig {
// Beware of circular dependencies between repositories and configuration properties beans.

	@Getter(value = NONE)
	private final AccountRoleRepo repo;
	@Getter(value = NONE)
	private final AccountConfig config;

	private boolean hasInit = false;

	private List<AccountRole> dbRoles;

	private AccountRole free;
	private AccountRole premium;
	private AccountRole moderator;
	private AccountRole admin;
	private AccountRole god;

	public synchronized void init() {

		dbRoles = (List<AccountRole>) repo.findAll();

		free = convert(config.getRoles().get("free"));
		premium = convert(config.getRoles().get("premium"));
		moderator = convert(config.getRoles().get("moderator"));
		admin = convert(config.getRoles().get("admin"));
		god = convert(config.getRoles().get("god"));

		hasInit = true;

	}

	public void check() {

		final List<String> dbStatusNames = dbRoles.stream()
				.map(AccountRole::getName)
				.collect(Collectors.toList());
		String msg;

		for (final String statusName : config.getRoles().values()) {

			if (!dbStatusNames.remove(statusName)) {

				throw new NoSuchElementException();

			}

		}

		if (!dbStatusNames.isEmpty()) {

			msg = dbStatusNames.stream().collect(Collectors.joining(","));
			log.warn("Following account roles are unsupported but present in storage : " + msg);

		}

	}

	private AccountRole convert(final String roleName) {

		return dbRoles.stream().filter(r -> r.getName().equalsIgnoreCase(roleName.trim()))
				.findFirst()
				.orElseThrow();

	}

	public List<AccountRole> getDbRoles() {

		return new ArrayList<>(dbRoles);
	}

}
