package org.ybygjy.jms.simpleptp;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.ybygjy.jms.JMSConstant;

/**
 * 负责消息发送，主要有以下状态流程：<br>
 * 1、取连接工厂<br>
 * 2、使用连接工厂创建连接<br>
 * 3、使用连接创建公话<br>
 * 4、使用会话创建Producer、Destination、Message<br>
 * 逻辑处理描述<br>
 * 1、创建服务线程,每隔一段时间传递一个消息<br>
 * 2、当满足某个条件时传递一个Control Message表示此次会话结束
 * @author WangYanCheng
 * @version 2012-3-1
 */
public class PTPSender extends Thread {
    /** 连接 */
    private QueueConnection queueConn;
    /** transacted Flag */
    private boolean transacted;
    /** acknowledge Model */
    private int acknowledge;
    /** queue Description */
    private String queueDesc;
    /** Session */
    private QueueSession queueSession;
    /** Destination */
    private Queue queue;
    /** message producer */
    private QueueSender queueSender;
    /** 发送20个消息之后线程关闭 */
    public static final int MAX_SEND_MESSAGE = 20;
    /**线程条件标记*/
    private volatile boolean isDown;
    /**
     * 构造函数，初始化连接
     * @param queueConn 连接实例
     * @param queueDesc 目的地标记
     * @param transacted 事务标记
     * @param acknowledge 消息确认模式
     */
    public PTPSender(QueueConnection queueConn, String queueDesc, boolean transacted, int acknowledge) {
        this.isDown = true;
        this.queueConn = queueConn;
        this.queueDesc = queueDesc;
        this.transacted = transacted;
        this.acknowledge = acknowledge;
        initContext();
    }

    /**
     * 初始化
     */
    private void initContext() {
        try {
            queueConn.start();
            queueSession = queueConn.createQueueSession(transacted, acknowledge);
            queue = queueSession.createQueue(this.queueDesc);
            queueSender = queueSession.createSender(queue);
        } catch (JMSException e) {
            System.err.println("与服务器建立连接失败==>".concat(this.queueConn.toString()));
            isDown = false;
        }
    }

    /**
     * 线程入口
     */
    public void run() {
        int innerMesCount = 0;
        while (isDown) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                innerMesCount++;
                queueSender.send(createTextMessage(queueSession));
                System.out.println("发送了".concat(String.valueOf(innerMesCount)).concat("条消息。。。"));
                if (innerMesCount == MAX_SEND_MESSAGE) {
                    /* 所谓的ControlMessage指的就是与普通Message有些差异的Message */
                    queueSender.send(queueSession.createTextMessage());
                    isDown = false;
                }
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
        /* 对queueConn的管理会存在缺陷,目前只考虑测试环境 */
        try {
            queueConn.close();
            System.out.println("关闭Connection>>".concat(this.queueConn.toString()));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建消息实例
     * @param session 会话实例
     * @return rtnMessage 消息实例
     * @throws JMSException 异常信息
     */
    private TextMessage createTextMessage(Session session) throws JMSException {
        TextMessage rtnMessage = session.createTextMessage();
        StringBuffer sbud = new StringBuffer();
        sbud.append(this.getName()).append("\n\t").append(String.valueOf(Math.random()));
        rtnMessage.setText(sbud.toString());
        return rtnMessage;
    }

    /**
     * 测试入口
     * @param args 参数列表
     * @throws JMSException JMSException
     */
    public static void main(String[] args) throws JMSException {
        QueueConnectionFactory qcfInst = new ActiveMQConnectionFactory(JMSConstant.JMS_URL);
        QueueConnection queueConn = qcfInst.createQueueConnection();
        PTPSender ptpInst = new PTPSender(queueConn, JMSConstant.JMS_QUEUE, false, Session.AUTO_ACKNOWLEDGE);
        ptpInst.start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
