package org.ybygjy.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ybygjy.logger.LoggerFactory;
import org.ybygjy.meta.model.ConstraintColumnMeta;
import org.ybygjy.meta.model.ConstraintMeta;
import org.ybygjy.meta.model.FieldMeta;
import org.ybygjy.meta.model.TableMeta;
import org.ybygjy.sql.SqlMgr;
import org.ybygjy.sql.model.SqlModel;


/**
 * SQLMgr MSSql实现
 * @author WangYanCheng
 * @version 2012-4-9
 */
public class SqlMgrImpl4MSSql implements SqlMgr {
    /**日志*/
    private Logger logger;
    /**
     * Constructor
     */
    public SqlMgrImpl4MSSql() {
        logger = LoggerFactory.getInstance().getLogger(this.getClass().getName());
    }

    @Override
    public SqlModel buildQrySQL(TableMeta tableMeta) {
        SqlModel smInst = new SqlModel();
        smInst.setTableMeta(tableMeta);
        AnalyseSql asInst = new AnalyseSql(tableMeta);
        smInst.setSqlStmt(asInst.analyseQuerySql());
        smInst.setSelectFields(asInst.getFieldMetaArr());
        return smInst;
    }

    @Override
    public SqlModel buildInsSQL(TableMeta tableMeta) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, SqlModel> buildQrySQL(List<TableMeta> tableMeta) {
        Map<String, SqlModel> rtnMap = new HashMap<String, SqlModel>();
        for (Iterator<TableMeta> iterator = tableMeta.iterator(); iterator.hasNext();) {
            TableMeta tmInst = iterator.next();
            rtnMap.put(tmInst.getTableName(), buildQrySQL(tmInst));
        }
        return rtnMap;
    }

    @Override
    public Map<String, SqlModel> buildInsSQL(List<TableMeta> tableMeta) {
        return null;
    }

    @Override
    public SqlModel buildQryClobSQL(TableMeta tableMeta) {
        String sqlTMPL = new String("SELECT @S FROM @T A");
        //取主键约束
        ConstraintMeta pkConst = tableMeta.getPrimaryConstraint();
        if (null == pkConst) {
            logger.log(Level.ALL, "表主键不存在，不能操作Clob类型数据!");
            throw new RuntimeException("表主键不存在，不能操作Clob类型数据!");
        }
        if (!tableMeta.hasSpecialType()) {
            logger.log(Level.ALL, "表中不包含特殊类型字段，不能操作Clob类型数据!");
            throw new RuntimeException(tableMeta.getTableName().concat("表中不包含特殊类型字段，不能操作Clob类型数据!"));
        }
        //约束字段
        List<ConstraintColumnMeta> constraintColumns  = pkConst.getConstraintColumn();
        StringBuilder qryColumns = new StringBuilder();
        //SQL语句中的列(主键,列1,列2)
        List<FieldMeta> fieldList = new ArrayList<FieldMeta>();
        //主键约束列
        for (ConstraintColumnMeta ccmInst : constraintColumns) {
            fieldList.add(ccmInst.getFieldMeta());
            qryColumns.append(ccmInst.getFieldMeta().getFieldCode()).append(",");
        }
        //表的特殊类型列
        for (FieldMeta fmInst : tableMeta.getSpecialTypeColumns()) {
            fieldList.add(fmInst);
            qryColumns.append(fmInst.getFieldCode()).append(",");
        }
        qryColumns.setLength(qryColumns.length() - 1);
        sqlTMPL = sqlTMPL.replaceAll("@S", qryColumns.toString()).replaceAll("@T", tableMeta.getTableName());
        SqlModel rtnSM = new SqlModel();
        rtnSM.setTableMeta(tableMeta);
        rtnSM.setSelectFields(fieldList.toArray(new FieldMeta[fieldList.size()]));
        rtnSM.setSqlStmt(sqlTMPL);
        return rtnSM;
    }

    @Override
    public SqlModel buildInsertClobSQL(TableMeta tableMeta) {
        //TODO 参照处理大字段查询SQL的处理
        //TODO 注意具体执行插入大字段的处理可以不相同
        //TODO 1、JDBC3采用查询更新方式主要是不支持Connection#createClob
        //TODO 2、JDBC4建议采用更新传递Clob参数完成
        return null;
    }
}
