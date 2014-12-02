package org.ybygjy.jndi.jldap;

import org.ybygjy.jndi.ldap.LDAPUtils;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

/**
 * JLDAP HelloWorld
 * @author WangYanCheng
 * @version 2011-5-12
 */
public class HelloJLDAP {
    private String ldapURL = "172.16.0.75";
    // private String ldapURL = "localhost";
    private String connDN = "cn=WangYanCheng,ou=People,dc=daowoo,dc=com";
    private int ldapPort = 389;
    private LDAPConnection ldapCtx;

    @SuppressWarnings("deprecation")
    public HelloJLDAP(String dn, String password) {
        ldapCtx = new LDAPConnection();
        try {
            ldapCtx.connect(ldapURL, ldapPort);
            ldapCtx.bind(LDAPConnection.LDAP_V3, dn == null ? connDN : dn, new String(
                password == null ? "123456" : password).getBytes());
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加
     * @throws LDAPException
     */
    public void create() throws LDAPException {
        LDAPAttributeSet attrSet = new LDAPAttributeSet();
        attrSet.add(new LDAPAttribute("objectclass", new String[] {"inetOrgPerson", "organizationalPerson",
                "person", "top"}));
        attrSet.add(new LDAPAttribute("cn", new String[] {"JLDAPTest"}));
        attrSet.add(new LDAPAttribute("sn", "jldap"));
        attrSet.add(new LDAPAttribute("userPassword", LDAPUtils.getOpenLDAPMD5("123456")));
        LDAPEntry entry = new LDAPEntry("cn=JLDAPTest,ou=People,dc=daowoo,dc=com", attrSet);
        ldapCtx.add(entry);
        ldapCtx.clone();
    }

    /**
     * 查询
     * @throws LDAPException
     */
    public void qryAttr() throws LDAPException {
        String[] attrs = new String[] {"cn", "userPassword"};
        LDAPSearchResults ldapResult = ldapCtx.search("dc=daowoo,dc=com", LDAPConnection.SCOPE_SUB,
            "cn=JLDAP", attrs, false);
        LDAPEntry tmpEntry = null;
        while (ldapResult.hasMore()) {
            tmpEntry = ldapResult.next();
            for (String attr : attrs) {
                LDAPAttribute attrT = tmpEntry.getAttribute(attr);
                System.out.println(attrT.getBaseName() + "\t" + attrT.getStringValue());
            }
        }
    }

    /**
     * 查询
     * @throws LDAPException
     */
    public void testSearch() throws LDAPException {
        LDAPSearchResults searchResult = ldapCtx.search("dc=daowoo,dc=com", LDAPConnection.SCOPE_SUB,
            "uid=*", null, false);
        int count = 0;
        while (searchResult.hasMore()) {
            LDAPEntry entry = searchResult.next();
            System.out.println(entry.getDN());
            count++;
        }
        System.out.println("共有记录：".concat(count + "") + "条");
    }

    public void release() {
        System.out.println("Over");
        if (ldapCtx.isConnected()) {
            try {
                ldapCtx.disconnect();
            } catch (LDAPException e) {
                e.printStackTrace();
            } finally {
                System.out.println("释放连接。。。");
            }
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     * @throws LDAPException LDAPException
     */
    public static void main(String[] args) throws LDAPException {
        HelloJLDAP jldapInst = new HelloJLDAP("cn=Manager,dc=daowoo,dc=com", "123456");
        jldapInst.create();
        /*jldapInst.qryAttr();
        jldapInst.testSearch();
        jldapInst.release();*/
    }
}
