package org.ybygjy.jms.simpleacknowlege;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ybygjy.jms.JMSConstant;

/**
 * 负责创建一个CLIENT_ACKNOWLEDGE模式的session
 * <p>CLIENT_ACKNOWLEDGE模式，即
 * @author WangYanCheng
 * @version 2012-3-8
 */
public class SynchSender extends Thread {
    /**连接实例*/
    private QueueConnection queueConn;
    /**会话实例*/
    private QueueSession queueSession;
    /**Destination*/
    private Queue queue;
    /**Message Producer*/
    private QueueSender queueSender;
    /**Queue Name*/
    private String queueName;
    /**线程运行标记*/
    private volatile boolean isDone;
    /**
     * Constructor
     * @param queueConn {@link QueueConnection}
     */
    public SynchSender(QueueConnection queueConn, String queueName) {
        this.queueConn = queueConn;
        this.queueName = queueName;
        initContext();
    }
    /**
     * 负责初始化基础实例
     */
    private void initContext() {
        try {
            this.queueSession = this.queueConn.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
            this.queue = this.queueSession.createQueue(queueName);
            this.queueSender = this.queueSession.createSender(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        int count = 0;
        while (!isDone) {
            //每隔1秒发送一条消息
            try {
                count ++;
                this.queueSender.send(createMessage());
                System.out.println("发送第".concat(String.valueOf(count)).concat("条消息！"));
                if (count >= 15) {
                    this.isDone = true;
                    this.queueSender.send(this.queueSession.createMessage());
                    System.out.println("退出任务。。。");
                }
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (isDone) {
            if (null != this.queueConn) {
                try {
                    this.queueConn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.exit(0);
                }
            }
        }
    }
    /**
     * 
     * @return
     * @throws JMSException
     */
    private Message createMessage() throws JMSException {
        Message rtnMessage = this.queueSession.createTextMessage("线程名称：".concat(getName()).concat("\t随机号：").concat(String.valueOf(Math.random())));
        return rtnMessage;
    }
    /**
     * 测试入口
     * @param args 参数列表
     * @throws JMSException JMSException
     */
    public static void main(String[] args) throws JMSException {
        QueueConnectionFactory queueConnFact = new ActiveMQConnectionFactory();
        QueueConnection queueConn = queueConnFact.createQueueConnection();
        SynchSender synchSender = new SynchSender(queueConn, JMSConstant.JMS_QUEUE);
        synchSender.start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
