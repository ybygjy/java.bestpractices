package org.ybygjy.jms.simpleps;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ybygjy.jms.JMSConstant;

/**
 * 负责消息发布
 * @author WangYanCheng
 * @version 2012-3-1
 */
public class TopicPublisher extends Thread {
    /** 连接 */
    private TopicConnection topicConn;
    /** transacted flag */
    private boolean transacted;
    /** acknowledge mode */
    private int acknowledgeMode;
    /** topic session */
    private TopicSession topicSession;
    /** 主题 */
    private String topicName;
    /** 内容发布实例 */
    private javax.jms.TopicPublisher topicPublisher;
    /** 线程运行标记 */
    private volatile boolean isDone;
    /** 发布消息阀值 */
    public static final int MAX_PUBLISH_MESS = 10;

    /**
     * 构造函数
     * @param topicConn 连接实例
     * @param topicName 主题描述
     * @param transacted 事务模式
     * @param acknowledgeMode 消息确认模式
     */
    public TopicPublisher(TopicConnection topicConn, String topicName, boolean transacted,
        int acknowledgeMode) {
        this.isDone = false;
        this.topicConn = topicConn;
        this.topicName = topicName;
        this.transacted = transacted;
        this.acknowledgeMode = acknowledgeMode;
        this.initContext();
    }

    /**
     * 初始化
     */
    public void initContext() {
        try {
            topicSession = topicConn.createTopicSession(transacted, acknowledgeMode);
            Topic topic = topicSession.createTopic(topicName);
            topicPublisher = topicSession.createPublisher(topic);
            topicConn.start();
        } catch (JMSException e) {
            System.err.println("初始化JMS错误：".concat(e.getMessage()));
            isDone = true;
        }
    }

    public void run() {
        int messCount = 0;
        while (!isDone) {
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                messCount++;
                this.topicPublisher.send(createMessage(this.topicSession));
                System.out.println("发布".concat(String.valueOf(messCount)).concat("条消息"));
                if (MAX_PUBLISH_MESS == messCount) {
                    isDone = true;
                    /* Message Control */
                    this.topicPublisher.send(this.topicSession.createTextMessage());
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        try {
            this.topicConn.close();
            System.out.println("关闭连接==>".concat(this.topicConn.toString()));
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 创建消息
     * @param topicSession 会话
     * @return rtnMessage 消息
     * @throws JMSException JMSException
     */
    private Message createMessage(TopicSession topicSession) throws JMSException {
        TextMessage rtnMessage = topicSession.createTextMessage();
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(getName()).append("\n\t").append(String.valueOf(Math.random()));
        rtnMessage.setText(sbuf.toString());
        return rtnMessage;
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
        new TopicPublisher(topicConn, JMSConstant.JMS_TOPIC, false, Session.AUTO_ACKNOWLEDGE).start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("程序退出。。。");
    }
}
