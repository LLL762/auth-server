package com.delacasa.auth.repo;

import java.util.List;

import com.delacasa.auth.entity.AccountStatus;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public interface AccountStatusRepo extends CustomRepository<AccountStatus, Integer> {

	List<AccountStatus> findAll();

}
