package org.ybygjy.pattern.proxy.virtual.simple;

/**
 * 代理主题
 * @author WangYanCheng
 * @version 2009-12-22
 */
public class ProxySubject implements Subject {
    /**realSubject*/
    private Subject realSubject = null;
    /**
     * {@inheritDoc}
     */
    public void doRequest() {
        if (realSubject == null) {
            realSubject = new RealSubject();
        }
        preRequest();
        realSubject.doRequest();
        postRequest();
    }
    /**
     * PreRequest
     */
    public void preRequest() {
        System.out.println("doPreRequest --> ProxySubject");
    }
    /**
     * PostRequest
     */
    public void postRequest() {
        System.out.println("doPostRequest --> ProxySubject");
    }
}
