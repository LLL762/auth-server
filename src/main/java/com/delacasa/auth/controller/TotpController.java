package com.delacasa.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.delacasa.auth.mail.EmailTotpConfig;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "${com.delacasa.auth.mail.totp-url}")
public class TotpController {

	private final EmailTotpConfig totpConfig;

	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable final Long id) {

		return new ModelAndView(totpConfig.getFormTemplate());

	}

	@PostMapping("/{id}")
	public String post(@PathVariable String id, @RequestParam final String totp, HttpServletRequest req) {

		System.out.println(totp);
		System.out.println(id);

		if (totp.length() != totpConfig.getTotpLength()) {

			return "redirect:" + totpConfig.getTotpUrl() + "/" + id;

		}

		return totpConfig.getFormTemplate();

	}

}
