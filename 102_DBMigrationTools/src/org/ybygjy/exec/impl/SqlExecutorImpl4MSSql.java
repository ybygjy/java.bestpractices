package org.ybygjy.exec.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.ybygjy.sql.model.SqlModel;


/**
 * SqlExecutor MSSql实现
 * @author WangYanCheng
 * @version 2012-4-9
 */
public class SqlExecutorImpl4MSSql extends AbstractSqlExecutor {
    /**
     * 构造函数初始化
     * @param conn 与数据库的连接对象
     */
    public SqlExecutorImpl4MSSql(Connection conn) {
        super(conn);
    }

    @Override
    public int executeInsert(SqlModel sqlModel, List<Map<String, Object>> dataMap) {
        logger.warning("该功能未实现#".concat(this.getClass().toString()));
        return 0;
    }
}
