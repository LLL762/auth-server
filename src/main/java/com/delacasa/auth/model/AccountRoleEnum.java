package com.delacasa.auth.model;

import lombok.Getter;

@Getter
public enum AccountRoleEnum {

	FREE("FREE_MEMBER"), PREMIUM("PREMIUM_MEMBER"), MODERATOR("MODERATOR"), ADMIN("ADMIN"), GOD("GOD");

	private final String name;

	AccountRoleEnum(final String name) {

		this.name = name;

	}

}
