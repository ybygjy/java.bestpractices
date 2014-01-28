package org.ybygjy.stype;

import java.sql.ResultSet;

import org.ybygjy.exec.SqlExecutor;
import org.ybygjy.exec.SqlExecutorListener;
import org.ybygjy.sql.model.SqlModel;

/**
 * 处理特殊类型，如CLob/Blob
 * @author WangYanCheng
 * @version 2012-10-15
 */
public interface STypeSqlExecutor {
    /**
     * 执行特殊类型查询
     * <p>
     * 主要是内部回调数据交互机制不一致，{@linkplain SqlExecutor#executeQuery(SqlModel)}
     * 内部回调是封装好的数据， 而该方法不能封装原始数据
     * @param sqlModel {@link SqlModel}
     * @return rtnCount 数据行数
     */
    public void executeQuerySpecialType(SqlModel sqlModel);

    /**
     * 执行特殊类型的插入语句
     * @param srcSqlModel 源查询结果集Sql对象{@link SqlModel}
     * @param targetSqlModel 分析执行Sql对象{@link SqlModel}
     * @param rs {@link ResultSet}
     */
    public int executeSpecialTypeInsert(SqlModel srcSqlModel, SqlModel targetSqlModel, ResultSet rs);
    /**
     * 添加事件侦听器
     * @param selInst {@link SqlExecutorListener}
     */
    public void addListener(SqlExecutorListener selInst);
}
