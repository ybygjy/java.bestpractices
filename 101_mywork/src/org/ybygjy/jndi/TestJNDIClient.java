package org.ybygjy.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * TestJNDI，测试环境<br/>
 * <ol>
 * <li>启动%JAVA_HOME%/bin/tnameserv.exe</li>
 * <li>运行TestJNDIServer</li>
 * <li>
 * <pre>java -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory
 *           -Djava.naming.provider.url=iiop://localhost:900
 *           org.ybygjy.jndi.TestJNDIClient
 * </pre>
 * </li>
 * </ol>
 * @author WangYanCheng
 * @version 2010-12-27
 */
public class TestJNDIClient {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        try {
            Hashtable env = new Hashtable();
            //env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
            env.put(Context.PROVIDER_URL, "iiop://localhost:900");
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
            Context ctx = new InitialContext(env);
            Object obj = ctx.lookup("/");
            System.out.println(obj);
            NamingEnumeration<NameClassPair> namingEnum = ctx.list("/");
            while (namingEnum.hasMore()) {
                System.out.println(namingEnum.next().getName());
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
