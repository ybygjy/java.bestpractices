package org.ybygjy.exec;

import java.util.List;
import java.util.Map;

import org.ybygjy.sql.model.SqlModel;


/**
 * 定义Sql执行规则
 * @author WangYanCheng
 * @version 2012-4-9
 */
public interface SqlExecutor {

    /**
     * 执行查询语句
     * @param sqlModel {@link SqlModel}
     * @return rtnCount 数据行数
     */
    public int executeQuery(SqlModel sqlModel);

    /**
     * 执行插入语句
     * @param sqlModel {@link SqlModel}
     * @param dataMap 数据集合
     */
    public int executeInsert(SqlModel sqlModel, List<Map<String, Object>> dataMap) ;

    /**
     * 添加事件侦听器
     * @param selInst {@link SqlExecutorListener}
     */
    public void addListener(SqlExecutorListener selInst);

    /**
     * 执行SQL语句
     * @param sql SQL语句
     */
    public void executeSQL(String sql);

    
    /**
     * 批量执行SQL语句
     * <strong>注意：因为是批量处理，所以如果其中有某条SQL出现错误，则会影响事批SQL</strong>
     * @param sql SQL语句集
     */
    public void executeSQL(String[] tmplSql);
}
