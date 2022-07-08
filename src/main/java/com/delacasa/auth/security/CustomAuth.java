package com.delacasa.auth.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.entity.AppResourceCategory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomAuth implements Authentication {

	private final AuthRequestDetails requestDetails;
	private Set<String> details;
	private Set<? extends GrantedAuthority> authorities;
	private String ipAdress;
	private String role;
	private Set<String> authorizations;
	private Set<String> restrictions;

	public void setUp(final Account account) {

		role = account.getRole().getName();

		authorizations = account.getAuthorizations().stream()
				.filter(a->a.)
		
				.map(a -> a.getAuthorization().getName() +  String.join(",",a.getAuthorization().getCategories();

	}

	private String formatCategList(final Set<AppResourceCategory> input) {

		return input.stream()
				.map(category -> String.valueOf(category.getId()))
				.collect(Collectors.joining(","));

	}

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
