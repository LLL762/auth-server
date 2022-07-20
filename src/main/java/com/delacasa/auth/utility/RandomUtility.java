package com.delacasa.auth.utility;

import java.util.Random;

import lombok.Getter;

public final class RandomUtility {

	@Getter
	private static final Random RANDOM_INSTANCE = new Random();

	private RandomUtility() {

	}

}
