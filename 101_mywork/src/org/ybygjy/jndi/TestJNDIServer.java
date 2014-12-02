package org.ybygjy.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * JNDI
 * @author WangYanCheng
 * @version 2010-12-27
 */
public class TestJNDIServer {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        Hashtable env = new Hashtable();
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
//        env.put(Context.PROVIDER_URL, "file:///tmp");
        env.put(Context.PROVIDER_URL, "iiop://localhost:900");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
        try {
            Context ctx = new InitialContext(env);
            ctx.createSubcontext("org.ybygjy");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
