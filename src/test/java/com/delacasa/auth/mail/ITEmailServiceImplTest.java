package com.delacasa.auth.mail;

import javax.mail.MessagingException;

import com.delacasa.auth.service.AccountService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ITEmailServiceImplTest {

	@Autowired
	private EmailServiceImpl emailService;

	@Autowired
	private AccountService accountService;

	@Test
	void test() throws MessagingException {

		emailService.sendCode(accountService.getAccountByUsernameOrMail("louis.vivier@hotmail.fr").get(), null);

	}

}
