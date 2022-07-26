package com.delacasa.auth.repo;

import org.springframework.stereotype.Repository;

import com.delacasa.auth.entity.AccountStatus;

@Repository
//@Scope("prototype")
public interface AccountStatusRepo extends CustomRepository<AccountStatus, Integer> {

	Iterable<AccountStatus> findAll();

}
