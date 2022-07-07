package com.delacasa.auth.model;

import lombok.Getter;

@Getter
public enum AccountRoleEnum {

	FREE("FREE"), PREMIUM("PREMIUM"), MODERATOR("MODERATOR"), ADMIN("ADMIN"), GOD("GOD");

	private final String name;

	AccountRoleEnum(final String name) {

		this.name = name;

	}

}
