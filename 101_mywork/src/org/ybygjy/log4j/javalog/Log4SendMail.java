package org.ybygjy.log4j.javalog;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.ybygjy.basic.TestInterface;

/**
 * 日志与邮件的整合
 * @author WangYanCheng
 * @version 2010-10-20
 */
public class Log4SendMail implements TestInterface {
    /** Logger */
    private static Logger logger;
    static {
        logger = Logger.getLogger(Log4SendMail.class.toString());
    }

    /**
     * {@inheritDoc}
     */
    public void doTest() {
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        try {
            String fileUrl = this.getClass().getResource("").toString();
            fileUrl += "Log4SendMail.xml";
            File tmpFile = new File(URI.create(fileUrl));
            logger.addHandler(new FileHandler(tmpFile.getAbsolutePath()));
            logger.addHandler(new ConsoleHandler());
            logger.addHandler(new Handler() {
                @Override
                public void close() {
                    return;
                }

                @Override
                public void flush() {
                    return;
                }

                @Override
                public void publish(LogRecord record) {
                    MailModule mmObj = new MailModule("ybygjy@gmail.com",
                        new String[] {"wangyancheng@ushayden.com"}, "Log4SendMailTest",
                        record.getMessage(), "smtp.gmail.com");
                    System.out.println(mmObj);
                    mmObj.doSendMail();
                }
            });
            logger.log(Level.INFO, "Hello JavaMail World.");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Log4SendMail log4SendMailInst = new Log4SendMail();
        log4SendMailInst.doTest();
    }

    /**
     * 负责邮件处理
     * @author WangYanCheng
     * @version 2010-10-20
     */
    class MailModule {
        /** 发送者 */
        private String fromAddr;
        /** 接收者 */
        private String[] receiveAddr;
        /** 主题 */
        private String subject;
        /** 内容 */
        private String content;
        /** 服务器地址 */
        private String serverAddr;

        /**
         * 构造函数
         * @param fromAddr fromAddr
         * @param receiveAddr receiveAddr
         * @param subject subject
         * @param content content
         * @param serverAddr serverAddr
         */
        public MailModule(String fromAddr, String[] receiveAddr, String subject, String content,
            String serverAddr) {
            this.fromAddr = fromAddr;
            this.receiveAddr = receiveAddr;
            this.subject = subject;
            this.content = content;
            this.serverAddr = serverAddr;
        }

        /**
         * 发送邮件
         */
        public void doSendMail() {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", this.serverAddr);
            Session session = Session.getDefaultInstance(prop, null);
            session.setPasswordAuthentication(new URLName("ybygjy"), new PasswordAuthentication("9722550199", ""));
            session.setDebug(true);
            Message mimeMsg = new MimeMessage(session);
            try {
                Address addressFrom = new InternetAddress(fromAddr);
                mimeMsg.setFrom(addressFrom);
                Address[] addressTo = new InternetAddress[receiveAddr.length];
                for (int i = 0; i < receiveAddr.length; i++) {
                    addressTo[i] = new InternetAddress(receiveAddr[i]);
                }
                mimeMsg.setRecipients(Message.RecipientType.TO, addressTo);
                mimeMsg.setSubject(subject);
                mimeMsg.setText(content);
                Transport.send(mimeMsg);
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("MailModule [content=");
            builder.append(content);
            builder.append(", fromAddr=");
            builder.append(fromAddr);
            builder.append(", receiveAddr=");
            builder.append(Arrays.toString(receiveAddr));
            builder.append(", serverAddr=");
            builder.append(serverAddr);
            builder.append(", subject=");
            builder.append(subject);
            builder.append("]");
            return builder.toString();
        }
    }
}
