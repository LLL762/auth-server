package com.delacasa.auth.repo;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface CustomRepository<T, I> extends Repository<T, I> {

	Optional<T> findById(I id);

}
