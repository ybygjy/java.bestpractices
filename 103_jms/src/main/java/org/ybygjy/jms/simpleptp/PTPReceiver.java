package org.ybygjy.jms.simpleptp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ybygjy.jms.JMSConstant;

/**
 * 负责接收消息
 * @author WangYanCheng
 * @version 2012-3-1
 */
public class PTPReceiver extends Thread {
    /** 连接实例 */
    private QueueConnection queueConn;
    /** destination */
    private String queueDesc;
    /** transacted flag */
    private boolean transacted;
    /** acknowledge model */
    private int acknowledge;
    /** session */
    private QueueSession queueSession;
    /** queue */
    private Queue queue;
    /** queueReceiver */
    private QueueReceiver receiver;
    /** 线程条件标记 */
    private volatile boolean isDown;

    /**
     * 构造函数
     * @param queueConn 连接实例
     * @param queueDesc 消息目的地描述
     * @param transacted 是否事务
     * @param acknowledge 消息确认模式
     */
    public PTPReceiver(QueueConnection queueConn, String queueDesc, boolean transacted, int acknowledge) {
        this.isDown = true;
        this.queueConn = queueConn;
        this.queueDesc = queueDesc;
        this.transacted = transacted;
        this.acknowledge = acknowledge;
        initContext();
    }

    /**
     * 基础内容初始化
     */
    private void initContext() {
        try {
            queueConn.start();
            queueSession = this.queueConn.createQueueSession(this.transacted, this.acknowledge);
            queue = queueSession.createQueue(this.queueDesc);
            receiver = queueSession.createReceiver(queue);
        } catch (JMSException e) {
            e.printStackTrace();
            isDown = false;
        }
    }

    /**
     * 线程入口
     */
    public void run() {
        int innerCount = 0;
        while (isDown) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                innerCount ++;
                Message srcMessage = receiver.receive();
                if (srcMessage instanceof TextMessage) {
                    TextMessage message = (TextMessage) srcMessage;
                    System.out.println("接收到消息".concat(String.valueOf(innerCount)).concat("\n").concat(message.toString()));
                    if (null == message.getText()) {
                        System.out.println("接收到结束消息，准备结束线程.");
                        this.isDown = false;
                    }
                } else {
                    System.out.println("未知类型的Message\n".concat(srcMessage.toString()));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        try {
            this.queueConn.close();
            System.out.println("关闭连接=>>" + this.queueConn.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     * @throws JMSException JMSException
     */
    public static void main(String[] args) throws JMSException {
        QueueConnectionFactory qcfInst = new ActiveMQConnectionFactory(JMSConstant.JMS_URL);
        QueueConnection queueConn = qcfInst.createQueueConnection();
        PTPReceiver ptpInst = new PTPReceiver(queueConn, JMSConstant.JMS_QUEUE, false, Session.AUTO_ACKNOWLEDGE);
        ptpInst.start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
