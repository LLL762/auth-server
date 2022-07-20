package com.delacasa.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "${com.delacasa.auth.mail.totp-url}")
public class TotpController {

	@GetMapping(value = "/{username}")
	@ResponseBody
	public String get(@PathVariable final String username) {

		return "hello" + username;

	}

}
