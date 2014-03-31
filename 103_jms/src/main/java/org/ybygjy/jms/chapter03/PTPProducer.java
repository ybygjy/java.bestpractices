package org.ybygjy.jms.chapter03;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ybygjy.jms.JMSConstant;

/**
 * It's a producer that used Point-to-Point Pattern.
 * @author WangYanCheng
 * @version 2012-2-28
 */
public class PTPProducer extends Thread {
    /**连接工厂_Administered Object*/
    private QueueConnectionFactory queueConnFactory;
    /**
     * 构造函数<br>
     * 初始化连接工厂
     */
    public PTPProducer() {
        queueConnFactory = new ActiveMQConnectionFactory(JMSConstant.JMS_URL);
    }
    /**
     * 逻辑操作入口
     */
    public void run() {
        while (true) {
            //创建连接
            QueueConnection queueConnection = null;
            try {
                queueConnection = queueConnFactory.createQueueConnection();
                //创建会话
                QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                //创建Destination
                Destination queueInst = queueSession.createQueue(JMSConstant.JMS_QUEUE);
                //创建Producer
                MessageProducer queueSender = queueSession.createSender((Queue) queueInst);
                queueConnection.start();
                //创建Message
                TextMessage textMessage = queueSession.createTextMessage("我是消息提供者。。。");
                queueSender.send(textMessage);
                System.out.println("发送了一个消息：" + textMessage);
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                if (null != queueConnection) {
                    try {
                        queueConnection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("执行完毕.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new PTPProducer().start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
