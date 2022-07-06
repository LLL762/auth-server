package com.delacasa.auth.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuth implements Authentication {

	private final AuthRequestDetails requestDetails;
	private Set<String> details;
	private Set<? extends GrantedAuthority> authorities;

	@Override
	public String getName() {

		return requestDetails.getUsernameOrEmail();
	}

	@Override
	public String getCredentials() {

		return requestDetails.getPassword();
	}

	@Override
	public String getPrincipal() {

		return requestDetails.getUsernameOrEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	public void setAuthorities(Set<? extends GrantedAuthority> authorities) {

		this.authorities = authorities;
	}

	@Override
	public Set<String> getDetails() {

		return details;
	}

	@Override
	public boolean isAuthenticated() {

		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

	}

}
