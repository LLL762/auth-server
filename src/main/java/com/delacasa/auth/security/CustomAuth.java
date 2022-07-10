package com.delacasa.auth.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.entity.AccountHasAuthorization;

import lombok.Getter;

@Getter
public class CustomAuth implements Authentication {

	private Map<String, String> details = new HashMap<>();
	private Set<GrantedAuthority> authorities = new HashSet<>();

	private final String name;
	private final String credentials;
	private final String principal;

	private boolean authenticated = false;

	public CustomAuth(final AuthRequestDetails requestDetails) {

		this.name = requestDetails.getUsernameOrEmail();
		this.principal = requestDetails.getUsernameOrEmail();
		this.credentials = requestDetails.getPassword();
		this.details.put("userIP", requestDetails.getUserIpAddress());

	}

	public void setUp(final Account account) {

		authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole().getName()));

		for (final AccountHasAuthorization hasAuthorization : account.getAuthorizations()) {

			final String authorizationName = hasAuthorization.getAuthorization().getName();
			final Long categoryID = hasAuthorization.getAuthorization().getId();

			authorities.add(hasAuthorization.isRestriction() ? new CategoryRestriction(authorizationName, categoryID)
					: new CategoryAuthority(authorizationName, categoryID));

		}

	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public String getCredentials() {

		return credentials;
	}

	@Override
	public String getPrincipal() {

		return principal;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public Map<String, String> getDetails() {

		return details;
	}

	@Override
	public boolean isAuthenticated() {

		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {

		Assert.isTrue(!authenticated,
				"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");

		this.authenticated = authenticated;

	}

}
