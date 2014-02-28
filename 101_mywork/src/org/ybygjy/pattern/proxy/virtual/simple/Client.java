package org.ybygjy.pattern.proxy.virtual.simple;



/**
 * Virtual Proxy
 * <p>使用ProxySubject来解耦Client与RealSubject</p>
 * <p><b>ProxySubject</b>负责准备RealSubject实例，当RealSubject非常耗时时这非常有用。</p>
 * @author WangYanCheng
 * @version 2009-12-22
 */
public class Client {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Subject subjectInst = new ProxySubClass();
        subjectInst.doRequest();
    }
}
