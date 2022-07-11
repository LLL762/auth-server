package com.delacasa.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.delacasa.auth.config.AccountConfig;
import com.delacasa.auth.entity.Account;
import com.delacasa.auth.entity.AccountHasAuthorization;
import com.delacasa.auth.jwt.JwtConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomAuthService {

	private final AccountConfig accountConfig;
	private final JwtConfig jwtConfig;

	public CustomAuth init(final CustomAuth auth, final AuthRequestDetails requestDetails) {

		auth.setName(requestDetails.getUsernameOrEmail());
		auth.setCredentials(requestDetails.getPassword());
		auth.putDetail(jwtConfig.getClaimIp(), requestDetails.getUserIpAddress());

		return auth;

	}

	public CustomAuth setUp(final CustomAuth auth, final Account account) {

		auth.setPrincipal(account.getId());
		auth.addAuthority(new SimpleGrantedAuthority(accountConfig.getRolesPrefix() + account.getRole().getName()));

		for (final AccountHasAuthorization hasAuthorization : account.getAuthorizations()) {

			final String authorizationName = hasAuthorization.getAuthorization().getName();
			final Long categoryID = hasAuthorization.getAuthorization().getId();

			auth.addAuthority(
					setUpCategoryAuthority(hasAuthorization.isRestriction(), categoryID, authorizationName));

		}

		return auth;

	}

	private String checkPrefix(final String prefix, final String name) {

		return name.startsWith(prefix) ? name : prefix + name;

	}

	private GrantedAuthority setUpCategoryAuthority(final boolean isRestriciton, final Long id, final String name) {

		final String prefix = isRestriciton ? accountConfig.getCategoryAuthorityPrefix() + id + "_"
				: accountConfig.getCategoryRestrictionPrefix() + id + "_";

		return new SimpleGrantedAuthority(checkPrefix(prefix, name));

	}

}
