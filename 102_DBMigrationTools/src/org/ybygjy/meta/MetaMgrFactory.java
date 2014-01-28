package org.ybygjy.meta;

import java.sql.Connection;
import java.util.List;

import org.ybygjy.meta.impl.MetaMgrImpl4MSSql;
import org.ybygjy.meta.impl.MetaMgrImpl4Oracle;


/**
 * MetaMgr工厂，封装了具体实例的实现
 * @author WangYanCheng
 * @version 2012-4-9
 */
public class MetaMgrFactory {
    /**
     * Singleton Pattern
     */
    private static MetaMgrFactory mmfInst = new MetaMgrFactory();

    /**
     * 取MetaMgr Oracle实现
     * @return rtnMgr {@link MetaMgr}
     */
    public MetaMgr getMetaMgr4Oracle(Connection conn) {
        return new MetaMgrImpl4Oracle(conn);
    }

    /**
     * 取MetaMgr MSSql实现
     * @return rtnMgr {@link MetaMgr}
     */
    public MetaMgr getMetaMgr4MSSql(Connection conn) {
        return new MetaMgrImpl4MSSql(conn);
    }

    /**
     * 取工厂实例
     * @return mmfInst {@link MetaMgrFactory}
     */
    public static final MetaMgrFactory getInstance() {
        return mmfInst;
    }
}
