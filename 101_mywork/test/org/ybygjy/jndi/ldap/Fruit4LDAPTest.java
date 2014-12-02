package org.ybygjy.jndi.ldap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ybygjy.jndi.ldap.model.Fruit;

/**
 * 对实现Reference接口的类实例的绑定
 * @author WangYanCheng
 * @version 2011-4-29
 */
public class Fruit4LDAPTest {
    /**服务实例*/
    private ContextMgr4LDAP cmLDAPInst;
    /**参与测试对象*/
    private Fruit fruit;
    @Before
    public void setUP() {
        cmLDAPInst = ContextMgr4LDAP.getInstance();
        fruit = new Fruit("苹果");
    }
    @Test
    public void testBind() {
        cmLDAPInst.bind(Fruit.dn, fruit);
    }
    @Test
    public void testLookup() {
        Fruit fruit = (Fruit) cmLDAPInst.lookup(Fruit.dn);
        if (!this.fruit.equals(fruit)) {
            throw new RuntimeException("绑定对象不一致。。");
        }
    }
    @Test
    public void testUnbind() {
        cmLDAPInst.unBind(Fruit.dn);
    }
    @After
    public void tearDown() {
    }
}
