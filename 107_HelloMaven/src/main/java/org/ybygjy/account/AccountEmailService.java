package org.ybygjy.account;

import javax.mail.MessagingException;

/**
 * 邮件服务
 * @author WangYanCheng
 */
public interface AccountEmailService {
	/**
	 * 发送邮件
	 * @param mailTo
	 * @param subject
	 * @param mailContent
	 * @throws MessagingException
	 */
	public void sendMail(String mailTo, String subject, String mailContent) throws MessagingException;
}
