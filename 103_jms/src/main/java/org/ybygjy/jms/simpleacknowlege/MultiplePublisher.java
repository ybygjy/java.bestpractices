package org.ybygjy.jms.simpleacknowlege;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ybygjy.jms.JMSConstant;

/**
 * 负责消息发布
 * @author WangYanCheng
 * @version 2012-3-16
 */
public class MultiplePublisher extends Thread {
    /** 连接实例 */
    private TopicConnection conn;
    /** 会话实例 */
    private TopicSession session;
    /** 主题实例 */
    private Topic topic;
    /** 消息发布实例 */
    private javax.jms.TopicPublisher topicPublisher;
    /** 主题名称 */
    private String topicName;
    /** 线程标记 */
    private volatile boolean isDone;
    /** 发布消息条数 */
    private static int JMS_MESS_MAX = 5;

    /**
     * 构造方法
     * @param conn 连接实例
     * @param topicName 主题名称
     */
    public MultiplePublisher(TopicConnection conn, String topicName) {
        this.conn = conn;
        this.topicName = topicName;
        isDone = false;
        initContext();
    }

    /**
     * 初始化
     */
    private void initContext() {
        try {
            this.session = this.conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            this.topic = this.session.createTopic(topicName);
            this.topicPublisher = this.session.createPublisher(topic);
            this.conn.start();
        } catch (JMSException e) {
            e.printStackTrace();
            isDone = true;
            System.err.println("初始化基础运行条件错误。");
        } finally {
        }
    }

    @Override
    public void run() {
        int msgCount = 0;
        while (!isDone) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Message messInst = this.createMessage(session);
                this.topicPublisher.publish(messInst);
                System.out.println("发布第[".concat(String.valueOf(msgCount ++)).concat("]条消息。。。"));
                if (msgCount >= JMS_MESS_MAX) {
                    this.topicPublisher.publish(session.createMessage());
                    System.out.println("发布控制类型消息，准备退出。。。");
                    this.isDone = true;
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if (isDone) {
            try {
                this.conn.close();
                System.out.println("关闭实例\n\n".concat(this.conn.toString()));
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                System.exit(0);
            }
        }
    }

    /**
     * 创建消息
     * @param session 会话实例
     * @return rtnMsg 消息实例
     * @throws JMSException 抛出异常做Log
     */
    private Message createMessage(Session session) throws JMSException {
        Message rtnMsg = session.createTextMessage(String.valueOf(Math.random()));
        return rtnMsg;
    }
    /**
     * 测试入口
     * @param args 参数列表
     * @throws JMSException JMSException
     */
    public static void main(String[] args) throws JMSException {
        TopicConnectionFactory topicConnFactory = new ActiveMQConnectionFactory();
        MultiplePublisher multipleInst = new MultiplePublisher(topicConnFactory.createTopicConnection(), JMSConstant.JMS_TOPIC);
        multipleInst.start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
