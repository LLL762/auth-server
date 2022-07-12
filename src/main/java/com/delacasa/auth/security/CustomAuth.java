package com.delacasa.auth.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomAuth implements Authentication {

	private final Map<String, String> details = new HashMap<>();
	private final Set<GrantedAuthority> authorities = new HashSet<>();

	private String name;
	private String credentials;
	private String principal;

	private boolean authenticated = false;

	public void addAuthority(final GrantedAuthority authority) {

		authorities.add(authority);

	}

	public void putDetail(final String key, final String value) {

		details.put(key, value);

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
