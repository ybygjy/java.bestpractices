package org.ybygjy.jndi.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * 共享LDAP属性设定
 * @author WangYanCheng
 * @version 2011-4-26
 */
public final class Constant {
//    public static final String LDAPURL = "ldap://172.16.0.73:389";
    public static final String LDAPURL = "ldap://172.16.0.75:389";
//    public static final String LDAPURL = "ldap://localhost:389/";
    public static final String BASEDN = "dc=daowoo,dc=com";
//    static final String BASEDN = "dc=ybygjy,dc=org";
//    static final String LDAPURL_COMP = "o=JNDITutorial,".concat(BASEDN);
    public static final String LDAPURL_COMP = BASEDN;
    public static final String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
//    static final String SECURITY_PRINCIPAL = "cn=Manager,dc=test,dc=com";
    public static final String SECURITY_PRINCIPAL = "cn=Manager,".concat(BASEDN);
    public static final String SECURITY_CREDENTIALS = "123456";
    public static final String SECURITY_AUTHENTICATION = "simple";
    public static final String SECURITY_AUTHENTICATION_DIGESTMD5 = "DIGEST-MD5";
    /**
     * getDirContext
     * <code>java -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory -Djava.naming.provider.url=ldap://localhost:389 -Djava.naming.security.authentication=simple -Djava.naming.security.principal=cn=Manager,dc=ybygjy,dc=org -Djava.naming.security.credentials=secret
     * </code>
     * @return DirContext
     */
    public static DirContext createCtx() {
        return innerCreateCtx(buildCtxEnv(null, null, null));
    }
    /**
     * InnerGetContext
     * @param env 环境
     * @return rtnCtx/null
     */
    private static DirContext innerCreateCtx(Hashtable<String, Object> env) {
        DirContext rtnCtx = null;
        try {
            rtnCtx = new InitialDirContext(env);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return rtnCtx;
    }
    /**
     * 封装创建连接目录服务环境配置
     * @param root 根
     * @param dn 给定dn
     * @param passwd 密码
     * @return env 环境配置
     */
    public static Hashtable<String, Object> buildCtxEnv(String root, String dn, String passwd) {
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, null == root || "".equals(root) ? LDAPURL : root);
        env.put(Context.SECURITY_PRINCIPAL, (null == dn || "".equals(dn) ? SECURITY_PRINCIPAL : dn));
        env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
        env.put(Context.SECURITY_CREDENTIALS, (null == passwd || "".equals(passwd) ? SECURITY_CREDENTIALS : passwd));
        return env;
    }
    
    /**
     * 适配绑定认证
     * @param dn 用户条目，<p><strong>请注意：</strong>此参数需要指定用户条目DN而不是单存的用户编码</p>
     * @param passwd 密码
     * @return ctx DirContext
     */
    public static DirContext createCtx(String dn, String passwd){
        return innerCreateCtx(buildCtxEnv(null, dn, passwd));
    }
}
