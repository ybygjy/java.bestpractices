package org.ybygjy.jms.simpleacknowlege;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ybygjy.jms.JMSConstant;

/**
 * 异步，消息接收
 * @author WangYanCheng
 * @version 2012-3-8
 */
public class AsynchSubscriber extends Thread {
    /** TopicConnection */
    private TopicConnection topicConn;
    /** TopicSession */
    private TopicSession topicSession;
    /** Topic */
    private Topic topic;
    /** TopicSubscriber */
    private TopicSubscriber topicSubscriber;
    /** Topic Name */
    private String topicName;
    /** isDone */
    private volatile boolean isDone;
    /** 实例标记 */
    private String subIdentify;

    /**
     * Constructor
     * @param topicConn 连接实例
     * @param topicName 主题名称
     */
    public AsynchSubscriber(TopicConnection topicConn, String topicName) {
        this.topicConn = topicConn;
        this.topicName = topicName;
        this.subIdentify = "DurableSubscriber_".concat(getName()).concat(String.valueOf(Math.random()));
        initContext();
    }

    /**
     * 初始化
     */
    private void initContext() {
        try {
            this.topicConn.setClientID(this.subIdentify);
            this.topicSession = this.topicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            this.topic = this.topicSession.createTopic(this.topicName);
            this.topicSubscriber = this.topicSession.createDurableSubscriber(topic, this.subIdentify);
            this.topicSubscriber.setMessageListener(new MessageListener() {
                /** 计数 */
                private int ctxCount = 0;

                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        System.out.println("收到".concat(String.valueOf(ctxCount++)).concat("条消息\n\t")
                            .concat(((TextMessage) message).toString()));
                    } else {
                        isDone = true;
                        System.out.println("收到标记消息，准备退出!\n\t".concat(message.toString()));
                    }
                }
            });
            this.topicConn.start();
        } catch (JMSException e) {
            this.isDone = true;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!this.isDone) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.isDone && null != this.topicConn) {
            try {
                this.topicSubscriber.close();
                this.topicSession.unsubscribe(this.subIdentify);
                this.topicConn.close();
                System.out.println("关闭连接：\n\t".concat(this.topicConn.toString()));
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                System.exit(0);
            }
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        TopicConnectionFactory topicConnFacory = new ActiveMQConnectionFactory();
        try {
            AsynchSubscriber asynchSubscriberInst = new AsynchSubscriber(topicConnFacory.createTopicConnection(), JMSConstant.JMS_TOPIC);
            asynchSubscriberInst.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
