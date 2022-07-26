package com.delacasa.auth.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.delacasa.auth.entity.AccountStatus;
import com.delacasa.auth.repo.AccountStatusRepo;

import lombok.Getter;

@Component
@ConfigurationPropertiesBinding
public class AccStatusPropConverter implements Converter<String, AccountStatus> {

	private AccountStatusRepo repo;
	@Getter
	private List<AccountStatus> dbStatus;
	@Getter
	private List<AccountStatus> configStatus;

	public AccStatusPropConverter(AccountStatusRepo repo) {
		this.repo = repo;
	}

	public void init() {

		dbStatus = repo.findAll();
		configStatus=
		
		
		
	}

	@Override
	public AccountStatus convert(final String source) {

		return;
	}

}
