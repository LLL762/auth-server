package com.delacasa.auth.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.delacasa.auth.entity.Account;

@Repository
public interface AccountRepo extends CrudRepository<Account, Long> {

	Optional<Account> findByUsername(final String username);

	Optional<Account> findByEmail(final String email);

}
