package com.delacasa.auth.mail;

import javax.mail.MessagingException;

import com.delacasa.auth.entity.Account;
import com.delacasa.auth.security.CustomAuth;

public interface TotpEmailService {

	void sendCode(final Account account, final CustomAuth auth) throws MessagingException;

}
