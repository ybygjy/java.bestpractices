package org.ybygjy.pattern.proxy.test3;

import org.ybygjy.pattern.proxy.test3.impl.JdbcDao;

/**
 * DaoFactory
 * @author WangYanCheng
 * @version 2009-12-31
 */
public class DaoFactory {
    /** singleton daoFactoryInst*/
    private static DaoFactory daoFactInst = new DaoFactory();
    /**
     * instance
     * @return daoFactInst
     */
    public static final DaoFactory instance() {
        return daoFactInst;
    }
    /**
     * getDaoInst
     * @return getDaoInst
     */
    public Dao getDaoInst() {
        Dao daoInst = new JdbcDao();
        return (Dao) (new CusInvocationHandler().doGetProxyInst(daoInst));
    }
}
