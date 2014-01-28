package org.ybygjy.sql;

import org.ybygjy.sql.impl.SqlMgrImpl4MSSql;
import org.ybygjy.sql.impl.SqlMgrImpl4Oracle;

/**
 * SQLMgr 工厂类，负责封装具体实例的创建
 * @author WangYanCheng
 * @version 2012-4-9
 */
public class SqlMgrFactory {
    /** singleton Pattern */
    private static SqlMgrFactory sqlMFInst = new SqlMgrFactory();

    /**
     * 取SqlMgr实例MSSql实现
     * @return rtnSqlMgr {@link SqlMgrImpl4MSSql}
     */
    public SqlMgr getSqlMgrImpl4MSSql() {
        return new SqlMgrImpl4MSSql();
    }

    /**
     * 取SqlMgr实例Oracle实现
     * @return rtnSqlMgr {@link SqlMgrImpl4Oracle}
     */
    public SqlMgr getSqlMgrImpl4Oracle() {
        return new SqlMgrImpl4Oracle();
    }

    /**
     * 取工厂实例
     * @return sqlMFInst 工厂实例
     */
    public static final SqlMgrFactory getInstance() {
        return sqlMFInst;
    }
}
