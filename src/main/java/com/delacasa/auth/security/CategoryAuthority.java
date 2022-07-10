package com.delacasa.auth.security;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class CategoryAuthority implements GrantedAuthority {

	private final String authority;
	private final long categoryId;
	private final String prefix;
	private static final String STATIC_PREFIX = "PERMISSION_ON_CATEGORY_";

	public CategoryAuthority(final String authority, final long categoriesId) {

		this.prefix = STATIC_PREFIX + categoriesId + "_";
		this.authority = setUpPrefix(authority);
		this.categoryId = categoriesId;

	}

	private String setUpPrefix(final String authority) {

		return authority.startsWith(prefix) ? authority : prefix + authority;

	}

	@Override
	public String getAuthority() {

		return authority;
	}

}
