package org.ybygjy.pattern.proxy.virtual.simple;


/**
 * ProxySubClass
 * @author WangYanCheng
 * @version 2009-12-22
 */
public class ProxySubClass extends ProxySubject {

    @Override
    public void postRequest() {
        super.postRequest();
        System.out.println("Extends super method postRequest -->" + this.getClass().getName());
    }

    @Override
    public void preRequest() {
        super.preRequest();
        System.out.println("Extends super method preRequest -->" + this.getClass().getName());
    }
}
