package com.delacasa.auth.service;

import java.util.Optional;

import com.delacasa.auth.entity.AccountStatus;

public interface AccountStatusService {

	Optional<AccountStatus> getByName(final String name);

	Iterable<AccountStatus> getAllStatus();

}
