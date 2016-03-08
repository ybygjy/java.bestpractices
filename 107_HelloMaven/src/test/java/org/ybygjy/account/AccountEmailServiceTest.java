package org.ybygjy.account;

import static org.junit.Assert.*;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * 单元测试
 * @author WangYanCheng
 * @version 2016-01-31
 */
public class AccountEmailServiceTest {
	private GreenMail greenMail;
	private String mailTo = "soldier.yancheng@163.com";
	private ServerSetup serverSetup;
	@Before
	public void setUp() throws Exception {
		this.serverSetup = new ServerSetup(8800, "localhost", "smtp");
		this.greenMail = new GreenMail(serverSetup);
		this.greenMail.setUser("ybygjy@gmail.com", "9722550199WL.");
		this.greenMail.start();
	}

	@After
	public void tearDown() throws Exception {
		this.greenMail.stop();
	}

	@Test
	public void test() {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("account-email.xml");
		AccountEmailService accountEmailService = (AccountEmailService) appCtx.getBean("accountEmailServer");
		String subject = "Hello Java Mail";
		String htmlText = "<h3>Mail Test.</h3>";
		try {
			accountEmailService.sendMail(this.mailTo, subject, htmlText);
			this.greenMail.waitForIncomingEmail(2000, 1);
			Message[] message = this.greenMail.getReceivedMessages();
			assertEquals(1, message.length);
			assertEquals(subject, message[0].getSubject());
			assertEquals(htmlText, GreenMailUtil.getBody(message[0]).trim());
			System.out.println(GreenMailUtil.getBody(message[0]));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
