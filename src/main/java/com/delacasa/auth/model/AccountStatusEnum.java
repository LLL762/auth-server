package com.delacasa.auth.model;

import lombok.Getter;

@Getter
public enum AccountStatusEnum {

	OK("OK"), LOCKED_AUTH("LOCKED_AUTH"), LOCKED_ADMIN("LOCKED_ADMIN"), BANNED("BANNED");

	private final String name;

	AccountStatusEnum(final String name) {

		this.name = name;

	}

}
