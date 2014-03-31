package org.ybygjy.jms.chapter02;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

/**
 * 负责消息的生产与发送
 * @author WangYanCheng
 * @version 2012-5-4
 */
public class ProducerTest {
    private static String jmsUrl = "tcp://192.168.3.93:61616";
    private static String topic = "org.ybygjy.jms";
    public static void main(String[] args) {
        ConnectionFactory cfInst = new ActiveMQConnectionFactory(jmsUrl);
        Connection conn = null;
        Session session = null;
        try {
            conn = cfInst.createConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = new ActiveMQQueue(topic);
            MessageProducer mpInst = session.createProducer(dest);
            TextMessage tmInst = session.createTextMessage("HelloWorld.");
            mpInst.send(tmInst);
            System.out.println("Send a Message!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
