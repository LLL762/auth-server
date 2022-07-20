package com.delacasa.auth.mail;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.delacasa.auth.service.AccountService;

@SpringBootTest
class ITEmailServiceImplTest {

	@Autowired
	private EmailServiceImpl emailService;

	@Autowired
	private AccountService accountService;

	@Test
	void test() throws MessagingException {

		emailService.sendCode(accountService.getAccountByUsernameOrMail("lamirallaurent@gmail.com").get(), null);
	}

}
