package org.ybygjy.exec;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.ybygjy.sql.model.SqlModel;

/**
 * 使用事件处理机制
 * @author WangYanCheng
 * @version 2012-4-9
 */
public interface SqlExecutorListener {
    /**
     * 事件处理机制，定义查询完成后调用
     * @param smInst {@link SqlModel}
     * @param dataMap 数据集
     */
    public void afterQuery(SqlModel smInst, List<Map<String, Object>> dataMap);
    /**
     * 执行针对特殊类型的插入语句
     * @param sqlModel {@link SqlModel}
     * @param rs 带有特殊类型的字段的结果集
     */
    public void afterSpecialTypeQuery(SqlModel sqlModel, ResultSet rs);
}
