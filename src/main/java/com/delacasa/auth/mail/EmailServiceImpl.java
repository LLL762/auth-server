package com.delacasa.auth.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.security.CustomAuth;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

	private final JavaMailSender mailSender;
	private final EmailTotpConfig emailTotpConfig;
	private final String subject = "One time only password from ";

	public void sendCode(final Account account, final CustomAuth auth) throws MessagingException {

		final MimeMessage message = mailSender.createMimeMessage();
		final MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(emailTotpConfig.getSender());
		helper.setTo(account.getEmail());
		helper.setText(generateContent(account, auth, "go"), true);

		mailSender.send(message);

	}

	private String generateContent(final Account account, final CustomAuth auth, final String totp) {

		return "<p>Hello " + account.getUsername() + "</p>"
				+ "<p>"
				+ "<p>For security reason, you're required to use the following "
				+ "One Time Password to login:</p>"
				+ "<p><b>" + "</b></p>"
				+ "<br>"
				+ "<p>Note: this OTP is set to expire in 5 minutes.</p>";

	}

}
