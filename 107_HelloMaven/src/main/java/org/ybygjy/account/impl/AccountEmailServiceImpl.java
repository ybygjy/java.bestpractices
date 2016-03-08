package org.ybygjy.account.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.ybygjy.account.AccountEmailService;

/**
 * 邮件服务实现
 * @author WangYanCheng
 * @version 2016-01-30
 */
public class AccountEmailServiceImpl implements AccountEmailService {
	private JavaMailSender javaMailSender;
	private String systemEmail;
	/*
	 * (non-Javadoc)
	 * @see org.ybygjy.account.AccountEmailService#sendMail(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void sendMail(String mailTo, String subject, String mailContent) throws MessagingException {
		MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
		MimeMessageHelper msgHelper = new MimeMessageHelper(mimeMessage);
		msgHelper.setFrom(this.systemEmail);
		msgHelper.setTo(mailTo);
		msgHelper.setSubject(subject);
		msgHelper.setText(mailContent, true);
		this.javaMailSender.send(mimeMessage);
	}
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public String getSystemEmail() {
		return systemEmail;
	}
	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
}
