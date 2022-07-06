package com.delacasa.auth.service;

import java.util.Optional;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.repo.AccountRepo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepo repo;

	public Optional<Account> getAccountByUsernameOrMail(final String usernameOrMail) {

		if (usernameOrMail.contains("@")) {

			return repo.findByEmail(usernameOrMail);

		}

		return repo.findByUsername(usernameOrMail);

	}

}
