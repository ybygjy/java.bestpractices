package org.ybygjy.jndi.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LDAPContextInitTest {
    private DirContext dirCtx = null;
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        if (null != dirCtx) {
            try {
                dirCtx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testInitContext() {
        Assert.assertNotNull(Constant.createCtx());
    }

    @Test
    public void testGetInitCtx4UP() {
        dirCtx = Constant.createCtx("cn=WangYanCheng,ou=People,dc=daowoo,dc=com", "123456");
        Assert.assertNotNull(dirCtx);
    }

    @Test
    public void testGetInitCtx4RUP() {
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, Constant.INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, Constant.LDAPURL.concat(""));
        env.put(Context.SECURITY_PRINCIPAL, /*JNDIConstant.SECURITY_PRINCIPAL*/"cn=WangYanCheng,ou=People,dc=daowoo,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, Constant.SECURITY_AUTHENTICATION);
        env.put(Context.SECURITY_CREDENTIALS, Constant.SECURITY_CREDENTIALS);
        try {
            dirCtx = new InitialDirContext(env);
            Assert.assertNotNull(dirCtx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
