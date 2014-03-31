package org.ybygjy.jms.chapter02;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

/**
 * 负责消息的消费
 * @author WangYanCheng
 * @version 2012-2-27
 */
public class ConsumerTest {
    private static String jmsUrl = "tcp://192.168.3.93:61616";
    private static String topic = "org.ybygjy.jms";
    public static void main(String[] args) {
        ConnectionFactory cfInst = new ActiveMQConnectionFactory(jmsUrl);
        Connection conn = null;
        Session session = null;
        try {
            conn = cfInst.createConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destInst = new ActiveMQQueue(topic);
            MessageConsumer mcInst = session.createConsumer(destInst);
            conn.start();
            Message message = mcInst.receive();
            TextMessage tmInst = (TextMessage) message;
            System.out.println("Received a message：" + tmInst.toString());
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
