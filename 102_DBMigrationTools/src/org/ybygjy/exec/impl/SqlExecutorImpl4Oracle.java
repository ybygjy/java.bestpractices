package org.ybygjy.exec.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.ybygjy.ctx.MigrationContextFactory;
import org.ybygjy.meta.model.FieldMeta;
import org.ybygjy.sql.model.SqlModel;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.SysConstants;

/**
 * SqlExecutor Oracle实现
 * @author WangYanCheng
 * @version 2012-4-9
 */
public class SqlExecutorImpl4Oracle extends AbstractSqlExecutor {
    /** ?正则标记 */
    private String sqlRegQM = "\\?";

    /**
     * 构造函数
     * @param conn 与数据库的连接
     */
    public SqlExecutorImpl4Oracle(Connection conn) {
        super(conn);
    }

    @Override
    protected int rawInsert(SqlModel sqlModel, List<Map<String, Object>> dataMapList) {
        int rtnCount = 0;
        Statement stmt = null;
        logger.info("启动，原始/经典策略执行SQL");
        try {
            stmt = conn.createStatement();
            for (Iterator<Map<String, Object>> iterator = dataMapList.iterator(); iterator.hasNext();) {
                Map<String, Object> dataMap = iterator.next();
                String sql = generalStandInsertSQL(sqlModel, dataMap);
                try {
                    stmt.execute(sql);
                    rtnCount++;
                } catch (Exception e) {
//                    logger.info("原始/经典方式执行SQL出错：".concat(e.getMessage()));
//                    logger.info("出错SQL语句：".concat(sql));
                    //TODO 此处有问题，需要考虑错误记录数超大的情况会造成OOM，可考虑采用外部文件I/O流的方式完善
                    MigrationContextFactory.getInstance().getCtx().appendSortedAttr(SysConstants.CTX_SQL_RAWINSERT_FAIL, sql);
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "原始SQL方式插入出错", e);
        } finally {
            DBUtils.close(stmt);
        }
        logger.info("完成，原始/经典策略执行SQL，共影响：".concat(String.valueOf(rtnCount)).concat("行！"));
        return rtnCount;
    }

    /**
     * 构建经典插入SQL
     * @param sqlModel {@link SqlModel}
     * @param dataMap 数据集
     * @return rtnSql SQL语句
     */
    private String generalStandInsertSQL(SqlModel sqlModel, Map<String, Object> dataMap) {
        SimpleDateFormat sdfInst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tmplSql = sqlModel.getSqlStmt();
        FieldMeta[] tmpParames = sqlModel.getSelectFields();
        String dateTmpl = "TO_DATE('@T', 'YYYY-MM-DD HH24:MI:SS')";
        for (FieldMeta fmInst : tmpParames) {
            String fieldCode = fmInst.getFieldCode();
            Object objValue = dataMap.get(fieldCode);
            switch (fmInst.getFieldType()) {
                case NUM:
                    tmplSql = tmplSql.replaceFirst(sqlRegQM,
                        objValue == null ? "0" : String.valueOf((Double) objValue));
                    break;
                case DATE:
                    String tmpStr = objValue == null ? "NULL" : dateTmpl.replace("@T",
                        sdfInst.format(new Date(((java.sql.Date) objValue).getTime())));
                    tmplSql = tmplSql.replaceFirst(sqlRegQM, tmpStr);
                    break;
                default:
                    // 默认都以字符串处理
                    tmplSql = tmplSql.replaceFirst(sqlRegQM,
                        objValue == null ? "NULL" : "'".concat(String.valueOf(objValue)).concat("'"));
                    break;
            }
        }
        return tmplSql;
    }
}
