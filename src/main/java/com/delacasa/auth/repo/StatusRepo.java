package com.delacasa.auth.repo;

import java.util.Optional;

import com.delacasa.auth.entity.AccountStatus;

public interface StatusRepo extends CustomRepository<AccountStatus, Long> {

	Optional<AccountStatus> getByName(final String name);

}
