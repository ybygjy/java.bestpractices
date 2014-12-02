package org.ybygjy.jndi.ldap;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * 属性测试
 * @author WangYanCheng
 * @version 2011-5-24
 */
public class AttributeMgr4LDAPTest {
    /**测试实例*/
    private AttributeMgr4LDAP attrMgr;
    /**目录服务对象*/
    private DirContext dirCtx;
    /**参与测试的DN*/
//    private String testDN = "cn=Londo Mollari,ou=People,o=JNDITutorial,dc=ybygjy,dc=org";
    public static final String testDN = "cn=WangYanCheng,ou=People,dc=daowoo,dc=com";
    @Before
    public void setUP() {
        dirCtx = Constant.createCtx();
        attrMgr = new AttributeMgr4LDAP(dirCtx);
    }

    @Test
    public void testQryAttr() {
        String[] keys = {"telephoneNumber", "mail", "facsimileTelephoneNumber", "objectClass", "sn", "cn"};
        Attributes attrArray = attrMgr.qryAttr(testDN, keys);
        NamingEnumeration<Attribute> nameEnum = (NamingEnumeration<Attribute>) attrArray.getAll();
        Assert.assertNotNull(nameEnum);
        LDAPUtils.printAttr(nameEnum);
    }
    @Test
    public void testModifyAttr() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("userId", String.valueOf(Math.random()));
        attrMgr.modifyAttr(testDN, DirContext.ADD_ATTRIBUTE, attrMap);
System.out.println("testModifyAttr#addAttribute successful!\t".concat("userID=").concat(attrMap.get("userId").toString()));
        attrMgr.modifyAttr(testDN, DirContext.REMOVE_ATTRIBUTE, attrMap);
System.out.println("testModifyAttr#removeAttribute successful!\t".concat("userID=").concat(attrMap.get("userId").toString()));
        attrMap.clear();
        Attributes attrBackup = attrMgr.qryAttr(testDN, new String[]{"userId"});
Assert.assertNotNull(attrBackup);
        Map<String, Object> tmpMap = new HashMap<String, Object>();
        tmpMap.put("userId", String.valueOf(Math.random()));
        attrMgr.modifyAttr(testDN, DirContext.REPLACE_ATTRIBUTE, tmpMap);
        //还原
        attrMgr.modifyAttr(testDN, DirContext.REPLACE_ATTRIBUTE, attrBackup);
    }

    @Test
    public void testUpdateAttr() throws NamingException {
        Attributes attrArray = attrMgr.qryAttr(testDN, new String[]{"telephoneNumber"});
        String telNumber = (String) attrArray.get("telephoneNumber").get();
        attrMgr.updateAttr(testDN, "telephoneNumber", "15810779839");
        attrArray = attrMgr.qryAttr(testDN, new String[]{"telephoneNumber"});
        Assert.assertNotSame(telNumber, (String) attrArray.get("telephoneNumber").get());
    }

    @Test
    public void testAddAttr() {
        attrMgr.addAttr(testDN, "sn", "中文");
    }

    @Test
    public void testRemoveAttr() {
        attrMgr.removeAttr(testDN, "sn", "中文");
    }
    @After
    public void tearDown() {
        if (dirCtx != null) {
            try {
                dirCtx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }
}
