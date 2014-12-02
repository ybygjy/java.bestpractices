package org.ybygjy.jndi.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * LDAP认证
 * @author WangYanCheng
 * @version 2011-5-12
 */
public class Auth4LDAP {
    /**LDAP上下文服务对象*/
    private DirContext dirCtx;
    /**
     * Constructor
     * @param dirCtx dirCtx
     */
    public Auth4LDAP(DirContext dirCtx) {
        this.dirCtx = dirCtx;
    }
    /**
     * 验证(查询/绑定方式)
     * @param userCode 用户编码
     * @param passwd 密码
     * @return {true：验证通过;false：验证失败}
     */
    public boolean auth4SBind(String userCode, String passwd) {
        boolean rtnFlag = false;
        //查询
        String filterStr = "(&(uid=@UID@)(userPassword=*))".replaceAll("@UID@", userCode);
        SearchControls scInst = new SearchControls();
        scInst.setSearchScope(SearchControls.SUBTREE_SCOPE);
        scInst.setReturningAttributes(new String[]{""});
        Context tmpCtx = null;
        try {
            NamingEnumeration<SearchResult> resultEnum = dirCtx.search(Constant.BASEDN, filterStr, scInst);
            SearchResult searchResult = null;
            if (resultEnum.hasMore()) {
                searchResult = resultEnum.next();
                //进行认证
                tmpCtx = Constant.createCtx(searchResult.getNameInNamespace(), passwd);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (tmpCtx != null) {
                rtnFlag = true;
                try {
                    tmpCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnFlag;
    }
    /**
     * 身份验证(使用直接绑定LDAP服务器策略)
     * @param userDn 全局条目地址
     * @param password 密码
     * @return {true:验证成功;false:验证失败}
     */
    public boolean auth4Bind(String userDn, String password) {
        return (null != Constant.createCtx(userDn, password));
    }
    /**
     * 取得某属性的值
     * @param attr 给定属性对象
     * @return 属性值,多个以逗号分割
     * @throws NamingException NamingException
     */
    public String getAttrValue(Attribute attr) throws NamingException {
        NamingEnumeration nameEnum = attr.getAll();
        StringBuilder sbud = new StringBuilder();
        while (nameEnum.hasMore()) {
            sbud.append(nameEnum.next().toString()).append(",");
        }
        return sbud.toString();
    }
}
