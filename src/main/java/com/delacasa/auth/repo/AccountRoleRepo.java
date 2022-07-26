package com.delacasa.auth.repo;

import com.delacasa.auth.entity.AccountRole;

public interface AccountRoleRepo extends CustomRepository<AccountRole, Long> {

	Iterable<AccountRole> findAll();

}
