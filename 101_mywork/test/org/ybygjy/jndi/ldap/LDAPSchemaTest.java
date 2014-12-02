package org.ybygjy.jndi.ldap;

import javax.naming.directory.DirContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * LDAP Schema测试
 * <p>
 * 对LDAP Schema测试在查询相关Schema属性是可以的，但在操纵Schema也就是更改并不可以， 原因在于LDAP Server
 * Schema更改、删除这块需要特殊的cn=config机制的处理，所以这块先放放(再议)
 * </p>
 * @author WangYanCheng
 * @version 2011-5-24
 */
public class LDAPSchemaTest {
    private LDAPSchemaWrapper ldapSchema;
    /** 参与测试DN */
    private String testDN = "cn=WangYanCheng,ou=People,dc=daowoo,dc=com";

    @Before
    public void setUp() throws Exception {
        ldapSchema = new LDAPSchemaWrapper(Constant.createCtx());
    }

    @After
    public void tearDown() throws Exception {
        ldapSchema = null;
    }

    @Test
    public void testGetSchema() {
        ldapSchema.getSchema(testDN);
    }

    @Test
    public void testGetSchemaDef() {
        ldapSchema.getSchemaClassDef(testDN);
    }

    @Test
    public void testGetAllSchemaClassDef() {
        ldapSchema.getSchemaClassDef4Lookup(testDN);
    }

    @Test
    public void testGetSchemaClassDef4Search() {
        ldapSchema.getSchemaClassDef4Search(testDN);
    }

    @Test
    public void testLookUp4ClassDef() {
        ldapSchema.lookUp4ClassDef(testDN, "person");
    }

    @Test
    public void testAddNewObjectClass() {
        Assert.assertTrue(ldapSchema.addNewObjectClass(testDN, "daowooUser", ldapSchema
            .createTestObjectClass()));
    }

    @Test
    public void testRemoveObjectClass() {
        Assert.assertTrue(ldapSchema.removeObjectClass(testDN, "person"));
    }

    @Test
    public void testModifyObjectClass() {
        Assert.assertTrue(ldapSchema.modifyObjectClass("", "person", DirContext.ADD_ATTRIBUTE, ldapSchema
            .createModifyObjectClass()));
    }

    @Test
    public void testGetAttributeDef() {
        ldapSchema.getSchemaAttributeDef(testDN);
    }

    @Test
    public void testRemoveAttributeDef() {
        ldapSchema.removeAttributeSchema("", "telephoneNumber");
    }

    @Test
    public void testSchemaAttributeDef() {
        String[] attrArray = {"sn", "cn", "telephoneNumber", "mail", "uid", "userPassword", "objectClass"};
        for (String str : attrArray) {
            ldapSchema.getSchemaAttributeDef4Lookup(testDN, str);
        }
    }

    @Test
    public void testGetSchemaSyntaxDef() {
        String syntaxID = "1.3.6.1.4.1.1466.115.121.1.15";
        String syntaxName = "Directory String";
        Assert.assertTrue(ldapSchema.getSchemaSyntaxDef(syntaxID));
        Assert.assertFalse(ldapSchema.getSchemaSyntaxDef(syntaxName));
    }
}
