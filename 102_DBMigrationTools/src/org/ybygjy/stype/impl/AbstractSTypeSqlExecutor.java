package org.ybygjy.stype.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ybygjy.exec.SqlExecutorListener;
import org.ybygjy.logger.LoggerFactory;
import org.ybygjy.meta.model.FieldMeta;
import org.ybygjy.sql.model.SqlModel;
import org.ybygjy.stype.STypeSqlExecutor;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.SysConstants;

/**
 * 抽象类_对特殊类型的处理
 * @author WangYanCheng
 * @version 2012-10-16
 */
public abstract class AbstractSTypeSqlExecutor implements STypeSqlExecutor {
    /** Logger */
    protected Logger logger;
    /** 与数据库的连接 */
    protected Connection conn;
    /** 侦听器 */
    protected SqlExecutorListener selInst;

    /**
     * 构造函数
     * @param conn 与数据库的连接
     */
    public AbstractSTypeSqlExecutor(Connection conn) {
        this.conn = conn;
        this.logger = LoggerFactory.getInstance().getLogger(getClass().getName().toString());
    }

    @Override
    public void executeQuerySpecialType(SqlModel sqlModel) {
        PreparedStatement pstmt = null;
        String sqlTMPL = null;
        if (sqlModel != null && (sqlTMPL = sqlModel.getSqlStmt()) != null) {
            return;
        }
        ResultSet rs = null;
        logger.info("执行查询#SQL语句：".concat(sqlTMPL));
        try {
            pstmt = this.conn.prepareStatement(sqlTMPL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                this.selInst.afterSpecialTypeQuery(sqlModel, rs);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "执行查询#出错：".concat(sqlTMPL), e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
    }

    @Override
    public int executeSpecialTypeInsert(SqlModel srcSqlModel, SqlModel targetSqlModel, ResultSet rs) {
        if (null == rs) {
            return -1;
        }
        int rtnCount = 0;
        // 我们能确认类似这种基础SQL（UPDATE TABLE A SET A.COL=? AND
        // A.KEY=?）的列索引Where条件一定在后面
        String sqlTmpl = targetSqlModel.getSqlStmt();
        // 取查询
        FieldMeta[] selectFields = targetSqlModel.getSelectFields();
        // 取Where条件
        FieldMeta[] whereFields = targetSqlModel.getWhereFields();
        PreparedStatement pstmt = null;
        int rowCount = 0;
        try {
            this.conn.setAutoCommit(false);
            pstmt = this.conn.prepareStatement(sqlTmpl);
            // 因为外层会调用一次rs.next()所以此处采用do/while
            do {
                int paramIndex = 1;
                paramIndex = setterClobParameter(pstmt, rs, selectFields, paramIndex);
                paramIndex = setterParamater(pstmt, rs, whereFields, paramIndex);
                pstmt.addBatch();
                rowCount++;
                if (rowCount > (SysConstants.MAX_CACHEITEM / 10 - 1)) {
                    executeBatch(pstmt, true);
                    logger.info("处理了".concat(String.valueOf(rowCount)).concat("条"));
                    rowCount = 0;
                }
                rtnCount++;
            } while (rs.next());
            if (rowCount != 0) {
                executeBatch(pstmt, true);
                rowCount = 0;
            }
        } catch (SQLException e) {
            this.logger.log(Level.WARNING, "插入逻辑错误", e);
            try {
                this.conn.rollback();
            } catch (SQLException e1) {
                this.logger.log(Level.WARNING, "回滚错误", e1);
            }
        } finally {
            if (pstmt != null) {
                DBUtils.close(pstmt);
            }
            try {
                this.conn.setAutoCommit(true);
            } catch (SQLException e) {
                this.logger.log(Level.WARNING, "开户Connection自动提交出错", e);
            }
        }
        return rtnCount;
    }

    @Override
    public void addListener(SqlExecutorListener selInst) {
        this.selInst = selInst;
    }

    /**
     * 传递普通类型执行参数
     * @param pstmt {@link PreparedStatement}
     * @param rs {@link ResultSet}
     * @param fms {@link FieldMeta}
     * @param paramIndex 参数索引
     * @return paramIndex 参数索引，用于后续传递其它执行参数
     * @throws SQLException {@link SQLException}
     */
    protected int setterParamater(PreparedStatement pstmt, ResultSet rs, FieldMeta[] fms, int paramIndex)
        throws SQLException {
        for (FieldMeta fm : fms) {
            switch (fm.getFieldType()) {
                case STR:
                    pstmt.setString(paramIndex++, rs.getString(fm.getFieldCode()));
                    break;
                case NUM:
                    pstmt.setDouble(paramIndex++, rs.getDouble(fm.getFieldCode()));
                    break;
                case DATE:
                    pstmt.setDate(paramIndex++, rs.getDate(fm.getFieldCode()));
                    break;
            }
        }
        return paramIndex;
    }

    /**
     * 传递Clob类型执行参数
     * @param pstmt {@link PreparedStatement}
     * @param rs {@link ResultSet}
     * @param fms {@link FieldMeta}
     * @param paramIndex 参数索引
     * @return paramIndex 参数索引，用于后续传递其它执行参数
     * @throws SQLException {@link SQLException}
     */
    protected abstract int setterClobParameter(PreparedStatement pstmt, ResultSet rs, FieldMeta[] fms,
                                               int paramIndex) throws SQLException;

    /**
     * 执行
     * @param pstmt {@link PreparedStatement}
     * @param canCommit 可否提交事务{true:是;false:否}
     * @throws SQLException {@link SQLException}
     */
    private void executeBatch(PreparedStatement pstmt, boolean canCommit) throws SQLException {
        verifyBatchUpdateStatus(pstmt.executeBatch());
        if (canCommit) {
            this.conn.commit();
        }
    }

    /**
     * 验证
     * @param flags 状态集
     */
    private void verifyBatchUpdateStatus(int[] flags) {
        StringBuilder sbud = new StringBuilder("共有 ".concat(String.valueOf(flags.length)).concat(
            " 条，其中异常状态 "));
        int ii = 0;
        for (int i : flags) {
            if (Statement.EXECUTE_FAILED == i) {
                ii++;
            }
        }
        if (ii > 0) {
            sbud.append(String.valueOf(ii)).append(" 条");
            this.logger.warning(sbud.toString());
        }
    }
}
