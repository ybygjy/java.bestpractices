package org.ybygjy.meta;

import java.util.List;
import java.util.Map;

import org.ybygjy.meta.model.ConstraintColumnMeta;
import org.ybygjy.meta.model.ConstraintMeta;
import org.ybygjy.meta.model.TableMeta;

//TODO 该接口太大，建议该接口依据职责进行拆分，如表管理相关、表约束元数据管理相关、表元数据管理相关
/**
 * 定义Meta管理
 * <p>
 * 1、负责Meta数据库与对象实体的转换
 * <p>
 * 2、负责Meta实体的组装
 * @author WangYanCheng
 * @version 2012-4-9
 */
public interface MetaMgr {
    /**
     * 取得当前连接数据库的用户SCHEMA所拥有的表对象
     * @return rtnList Map结构存储的表对象集
     */
    public Map<String, TableMeta> getAllTableMeta();

    /**
     * 取得给定名称的表对象
     * @param tableName 表对象
     * @return rtnTableMeta {@link TableMeta}
     */
    public TableMeta getTableMeta(String tableName);

    /**
     * 禁用表的约束
     * @param tableName 表名称
     * @param consStr 约束名称
     */
    public void disableTableConstraint(String tableName, String consStr);

    /**
     * 批量禁用表编码集合中的表的所有约束
     * @param tableNames 表编码集合
     */
    public void disableTableConstraint(String[] tableNames);

    /**
     * 启用表的约束
     * @param tableName 表名称
     * @param consStr 约束名称
     */
    public void enableTableConstraint(String tableName, String consStr);

    /**
     * 批量启用表编码集合中的表的所有约束
     * @param tableNames 表编码集合
     */
    public void enableTableConstraint(String[] tableNames);

    /**
     * 取表关联的约束
     * @param tableName 表名称
     * @return rtnConsArr 约束名称组/null
     */
    public String[] getTableConstraints(String tableName);

    /**
     * 禁用表关联的约束
     * @param tableName 表名称
     */
    public void disableTableConstraint(String tableName);

    /**
     * 启用表关联的约束
     * @param tableName 表名称
     */
    public void enableTableConstraint(String tableName);

    /**
     * 取对象约束
     * @param tableMeta {@link TableMeta}
     * @return rtnList {@link ConstraintMeta}
     */
    public List<ConstraintMeta> getConstraints(TableMeta tableMeta);

    /**
     * 取约束字段
     * @param cmInst {@link ConstraintColumnMeta}
     * @return rtnList {@link ConstraintColumnMeta}
     */
    public List<ConstraintColumnMeta> getConstraintsColumns(ConstraintMeta cmInst);

    /**
     * 禁用表的触发器
     * @param tableName 表名称
     */
    public boolean disableTableTriggers(String tableName);

    /**
     * 启用表的触发器
     * @param tableName 表名称
     */
    public boolean enableTableTriggers(String tableName);

    /**
     * 重置表序列
     * @param tableName 表名称
     */
    public void resetTableSequence(String tableName);
}
