package org.ybygjy.jndi.fs;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * SimpleTest
 * @author WangYanCheng
 * @version 2011-1-6
 */
public class SimpleTest {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, "file:/tmp");
        try {
            Context ctx = new InitialContext(env);
            Fruit fruit = new Fruit("王延成");
            ctx.rebind("fruit", fruit);
            Object obj = ctx.lookup("fruit");
System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
