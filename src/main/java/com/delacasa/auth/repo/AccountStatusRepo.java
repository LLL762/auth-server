package com.delacasa.auth.repo;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.delacasa.auth.entity.AccountStatus;

@Repository
@Scope("prototype")
public interface AccountStatusRepo extends CustomRepository<AccountStatus, Long> {

	List<AccountStatus> findAll();

}
