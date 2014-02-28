package org.ybygjy.jndi.ldap;

import javax.naming.directory.DirContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * 认证测试
 * @author WangYanCheng
 * @version 2011-5-12
 */
public class Auth4LDAPTest {
    /**参与测试实例*/
    private Auth4LDAP authInst;
    private DirContext dirCtx;
    private String userCode;
    private String password;
    private String userDN;
    @Before
    public void setUp() throws Exception {
        dirCtx = Constant.createCtx();
        authInst = new Auth4LDAP(dirCtx);
        userCode = "wangyancheng";
        password = "123456";
        userDN = "cn=@UID@,ou=People,dc=daowoo,dc=com".replaceAll("@UID@", userCode);
    }

    @After
    public void tearDown() throws Exception {
        authInst = null;
        dirCtx.close();
    }

    @Test
    public void testAuth4SBind() {
        Assert.assertTrue(authInst.auth4SBind(userCode, password));
    }
    @Test
    public void testAuth4Bind() {
        Assert.assertTrue(authInst.auth4Bind(userDN, password));
    }
}
