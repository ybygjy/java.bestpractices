package org.ybygjy.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件鉴权  
 * @author 2013159
 *
 */
public class SelfAuthenticator extends Authenticator{
	String userName=null;
	String password=null;
	 
	public SelfAuthenticator(){
	}
	public SelfAuthenticator(String username, String password) { 
		this.userName = username; 
		this.password = password; 
	} 
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName, password);
	}
}
 
