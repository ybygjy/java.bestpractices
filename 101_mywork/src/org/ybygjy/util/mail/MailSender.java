package org.ybygjy.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* 邮件发送器 
* @Author h
*/ 
public class MailSender  {
	//日志
	private static Logger logger = LoggerFactory.getLogger(MailSender.class);
/** 
  * 以文本格式发送邮件 
  * @param mailInfo 待发送的邮件的信息 
  */ 
	public static boolean sendTextMail(MailSenderInfo mailInfo) { 
	  // 判断是否需要身份认证 
	  SelfAuthenticator authenticator = null; 
	  Properties pro = mailInfo.getProperties();
	  if (mailInfo.isValidate()) { 
	  // 如果需要身份认证，则创建一个密码验证器 
		authenticator = new SelfAuthenticator(mailInfo.getUserName(), mailInfo.getPassword()); 
	  }
	  // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
	  Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
	  try { 
	  // 根据session创建一个邮件消息 
	  Message mailMessage = new MimeMessage(sendMailSession); 
	  // 创建邮件发送者地址 
	  Address from = new InternetAddress(mailInfo.getFromAddress()); 
	  // 设置邮件消息的发送者 
	  mailMessage.setFrom(from); 
	// 创建邮件的接收者地址，并设置到邮件消息中 
	  mailMessage.setRecipients(Message.RecipientType.TO,buildReceiveAddresses(mailInfo.getToAddresses())); 
	  // 设置邮件消息的主题 
	  mailMessage.setSubject(mailInfo.getSubject()); 
	  // 设置邮件消息发送的时间 
	  mailMessage.setSentDate(new Date()); 
	  // 设置邮件消息的主要内容 
	  String mailContent = mailInfo.getContent(); 
	  mailMessage.setText(mailContent); 
	  // 发送邮件 
	  Transport.send(mailMessage);
	  return true; 
	  } catch (MessagingException ex) { 
		  logger.error(ex.getMessage());
		  ex.printStackTrace(); 
	  } 
	  return false; 
	} 
	
	
	
	
	/** 
	  * 发送邮件
	  * 功能如下：
	  * 1、可以携带附件
	  * 2、支持HTML
	  * 3、支持发送多人
	  * @param mailInfo 待发送的邮件的信息 
	  */ 
		public static boolean sendMail(MailSenderInfo mailInfo) { 
		  // 判断是否需要身份认证 
		  SelfAuthenticator authenticator = null; 
		  Properties pro = mailInfo.getProperties();
		  if (mailInfo.isValidate()) { 
		  // 如果需要身份认证，则创建一个密码验证器 
			authenticator = new SelfAuthenticator(mailInfo.getUserName(), mailInfo.getPassword()); 
		  }
		  // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
		  Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
		  try { 
		  // 根据session创建一个邮件消息 
		  Message mailMessage = new MimeMessage(sendMailSession); 
		  // 创建邮件发送者地址 
		  Address from = new InternetAddress(mailInfo.getFromAddress()); 
		  // 设置邮件消息的发送者 
		  mailMessage.setFrom(from); 
		  // 创建邮件的接收者地址，并设置到邮件消息中 
		  mailMessage.setRecipients(Message.RecipientType.TO,buildReceiveAddresses(mailInfo.getToAddresses())); 
		  // 设置邮件消息的主题 
		  mailMessage.setSubject(mailInfo.getSubject()); 
		  // 设置邮件消息发送的时间 
		  mailMessage.setSentDate(new Date()); 
		  // 设置邮件消息的主要内容 
		  mailMessage.setContent(createContent(mailInfo.getContent(),mailInfo.getAttachFileNames()));
		  // 发送邮件 
		  Transport.send(mailMessage);
		  return true; 
		  } catch (MessagingException ex) { 
			  ex.printStackTrace(); 
		  } 
		  return false; 
		} 
		
		private static Address[] buildReceiveAddresses(String[] tos){
			Address[] addresses = new Address[tos.length];
			for(int i = 0;i<tos.length;i++){
				try {
					addresses[i] = new InternetAddress(tos[i]);
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
			return addresses;
		}
		
		/**  
	     * 根据传入的文件路径创建附件并返回  
	     */ 
	    private static MimeBodyPart createAttachment(String fileName) throws Exception {  
	        MimeBodyPart attachmentPart = new MimeBodyPart();  
	        FileDataSource fds = new FileDataSource(fileName);  
	        attachmentPart.setDataHandler(new DataHandler(fds));  
	        attachmentPart.setFileName(MimeUtility.encodeText(fds.getName())); 
	        return attachmentPart;  
	    } 
	    
	    /**  
	     * 根据传入的邮件正文body和文件路径创建图文并茂的正文部分  
	     * @throws MessagingException 
	     */ 
	    /**
	     * @param body
	     * @param files
	     * @return
	     * @throws MessagingException
	     */
	    private static MimeMultipart createContent(String body, String[] files) throws MessagingException{  
	        // 用于保存最终正文部分  
	        // 用于组合文本和图片，"related"型的MimeMultipart对象  
	        MimeMultipart allPart = new MimeMultipart("mixed");  
	        // 正文的文本部分  
	        MimeBodyPart textBody = new MimeBodyPart();  
	        textBody.setContent(body, "text/html;charset=utf-8");  
	        allPart.addBodyPart(textBody);  
	        if(files!=null && files.length>0){
				// 将邮件中各个部分组合到一个"mixed"型的 MimeMultipart 对象
	        	  int index = 1; 
			      for(String fileName:files){
			    	  if(StringUtils.isNotBlank(fileName))
						try {
							MimeBodyPart bodyPart = createAttachment(fileName);
							if(fileName.endsWith("jpg")){
								bodyPart.setContentID((index++)+"");
								
							}
							allPart.addBodyPart(bodyPart);
						} catch (Exception e) {
							e.printStackTrace();
						}  
			      }
			  }
	        return allPart;  
	    }
	    public static void main(String[] args) {
	    	String[] tos = {"ybygjy@gmail.com"};
	    	String zipPath = "/Users/MLS/";
	    	String zipName = "tmp_tar.tar";
	    	MailSenderInfo mailSenderInfo = new MailSenderInfo(tos, zipName,"请查收附件", new String[] { zipPath + zipName });
			MailSender.sendMail(mailSenderInfo);
			System.out.println("发送成功");
	    }
} 
