package org.ybygjy.jms.simpleps;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ybygjy.jms.JMSConstant;

/**
 * 负责订阅消息
 * @author WangYanCheng
 * @version 2012-3-1
 */
public class TopicSubscriber extends Thread {
    /** 连接 */
    private TopicConnection topicConn;
    /** 会话 */
    private TopicSession session;
    /** 订阅者 */
    private javax.jms.TopicSubscriber topicSubscriber;
    /** 消息主题 */
    private Topic topic;
    /** 事务标识 */
    private boolean transacted;
    /** 消息接收确认模式 */
    private int acknowledgeMode;
    /** 消息主题 */
    private String topicName;
    /** 线程执行标记 */
    private volatile boolean isDone;

    /**
     * 构造函数
     * @param topicConn 连接
     * @param topicName 消息主题
     * @param transacted 事务标识
     * @param acknowledgeMode 消息接收确认模式
     */
    public TopicSubscriber(TopicConnection topicConn, String topicName, boolean transacted,
        int acknowledgeMode) {
        this.isDone = false;
        this.topicConn = topicConn;
        this.topicName = topicName;
        this.transacted = transacted;
        this.acknowledgeMode = acknowledgeMode;
        initContext();
    }

    /**
     * 初始化
     */
    private void initContext() {
        try {
            session = this.topicConn.createTopicSession(transacted, acknowledgeMode);
            topic = session.createTopic(topicName);
            topicSubscriber = session.createSubscriber(topic);
            this.topicSubscriber.setMessageListener(new MessageListener() {
                /** Message Count */
                private int messageCount = 0;

                @Override
                public void onMessage(Message message) {
                    messageCount++;
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("接收到第".concat(String.valueOf(messageCount)).concat("条消息==>")
                            .concat(textMessage.toString()));
                        try {
                            if (null == textMessage.getText()) {
                                System.out.println("收到关闭消息，准备退出。。。");
                                TopicSubscriber.this.isDone = true;
                            }
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("其它类型的消息==>".concat(message.toString()));
                    }
                }
            });
            this.topicConn.start();
        } catch (JMSException e) {
            this.isDone = true;
            e.printStackTrace();
        }
    }

    /**
     * 线程执行入口
     */
    @Override
	public void run() {
        while (!isDone) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            this.topicConn.close();
            System.out.println("关闭连接==>".concat(this.topicConn.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        TopicConnectionFactory tcfInst = new ActiveMQConnectionFactory(JMSConstant.JMS_URL);
        TopicConnection topicConn = null;
        try {
            topicConn = tcfInst.createTopicConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        new TopicSubscriber(topicConn, JMSConstant.JMS_TOPIC, false, Session.AUTO_ACKNOWLEDGE).start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("程序退出。。。");
    }
}
