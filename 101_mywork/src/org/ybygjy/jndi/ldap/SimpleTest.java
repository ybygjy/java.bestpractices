package org.ybygjy.jndi.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

/**
 * 负责验证一些忘记的API
 * @author WangYanCheng
 * @version 2011-6-9
 */
public class SimpleTest {
    private DirContext dirCtx;
    private String dn = "ou=People,dc=daowoo,dc=com";

    /**
     * Constructor
     * @param dirCtx dirCtx
     */
    public SimpleTest(DirContext dirCtx) {
        this.dirCtx = dirCtx;
    }
    /**
     * 验证获取指定条目的属性，小结
     * <p>1、可以为空，则表示取条目所有属性；</p>
     * <p>2、可以指定过滤属性组，用于取得某一小块属性；</p>
     */
    public void testGetAttr() {
        String tmpDN = "cn=WangYanCheng,".concat(dn);
        try {
            Attributes attrs = this.dirCtx.getAttributes(tmpDN, null);
            LDAPUtils.printAttr((NamingEnumeration<Attribute>) attrs.getAll());
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试查询API，小结
     * <p>
     * 1、search这些API都是针对树进行搜索的，如搜索给定DN的下一级叶子、下N级叶子;
     * </p>
     * <p>
     * 2、查询的都是相对给定DN的叶子，而不是DN本身的属性
     * </p>
     * <p>
     * 3、可设置相关查询参数来过滤叶子
     * </p>
     */
    public void testSearch() {
        try {
            Attributes attrs = new BasicAttributes(true);
            attrs.put(new BasicAttribute("cn", "WangYanCheng"));
            NamingEnumeration<SearchResult> searchResultEnum = this.dirCtx.search(dn, attrs,
                new String[] {"userPassword"});
            // LDAPUtils.printEnum(searchResultEnum);
            if (searchResultEnum.hasMore()) {
                SearchResult searchResult = searchResultEnum.next();
                LDAPUtils.printAttr((NamingEnumeration<Attribute>) searchResult.getAttributes().getAll());
            }

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        SimpleTest verifyInst = new SimpleTest(Constant.createCtx());
        verifyInst.testSearch();
        verifyInst.testGetAttr();
    }
}
