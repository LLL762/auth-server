package com.delacasa.auth.mail;

import static java.time.LocalDateTime.now;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.security.CustomAuth;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Service
@RequiredArgsConstructor
public class TotpEmailServiceImpl implements TotpEmailService {

	private final JavaMailSender mailSender;
	private final EmailTotpConfig emailTotpConfig;
	private final PasswordEncoder passwordEncoder;
	private final String subject = "One time only password from ";

	public void sendCode(final Account account, final CustomAuth auth) throws MessagingException {

		final MimeMessage message = mailSender.createMimeMessage();
		final MimeMessageHelper helper = new MimeMessageHelper(message);
		final String totp = RandomString.make(emailTotpConfig.getTotpLength());

		helper.setFrom(emailTotpConfig.getSender());
		helper.setSubject(subject);
		helper.setTo(account.getEmail());
		helper.setText(generateContent(account, auth, totp), true);

		mailSender.send(message);

		account.setTotp(passwordEncoder.encode(totp));
		account.setTotpExpiration(now().plusSeconds(emailTotpConfig.getExpirationInSeconds()));

	}

	private String generateContent(final Account account, final CustomAuth auth, final String totp) {

		return "<p>Hello " + account.getUsername() + "</p>"
				+ "<p>"
				+ "<p>For security reason, you're required to use the following "
				+ "One Time Password to login:</p>"
				+ "<p><b>" + totp + "</b></p>"
				+ "<br>"
				+ "<a href=http://localhost:8080/totp/" + account.getId() + "> click </a>"
				+ "<p>Note: this OTP is set to expire in 5 minutes.</p>";

	}

}
