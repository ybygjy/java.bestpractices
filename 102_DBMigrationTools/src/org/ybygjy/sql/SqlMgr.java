package org.ybygjy.sql;

import java.util.List;
import java.util.Map;

import org.ybygjy.meta.model.TableMeta;
import org.ybygjy.sql.model.SqlModel;


/**
 * 负责定义关于SQL语句的管理
 * @author WangYanCheng
 * @version 2012-4-9
 */
public interface SqlMgr {
    /**
     * 构建查询SQL
     * @param tableMeta {@link TableMeta}
     * @return sqlStr SQL语句
     */
    public SqlModel buildQrySQL(TableMeta tableMeta);

    /**
     * 构建插入SQL
     * @param tableMeta {@link TableMeta}
     * @return sqlStr SQL语句
     */
    public SqlModel buildInsSQL(TableMeta tableMeta);

    /**
     * 构建查询SQL
     * @param tableMeta {@link TableMeta}
     * @return sqlStr SQL语句
     */
    public Map<String, SqlModel> buildQrySQL(List<TableMeta> tableMeta);

    /**
     * 构建插入SQL
     * @param tableMeta {@link TableMeta}
     * @return sqlStr SQL语句
     */
    public Map<String, SqlModel> buildInsSQL(List<TableMeta> tableMeta);

    /**
     * 构建查询{@link java.sql.Clob}类型字段
     * @param tableMeta {@link TableMeta}
     * @return rtnSMInst SQL语句实例
     * @see SqlModel
     */
    public SqlModel buildQryClobSQL(TableMeta tableMeta);

    /**
     * 构建插入SQL
     * @param tableMeta {@link TableMeta}
     * @return rtnSMInst SQL语句实例
     * @see SqlModel
     */
    public SqlModel buildInsertClobSQL(TableMeta tableMeta);
}
