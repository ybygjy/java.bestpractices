package org.ybygjy.basic.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 测试Statement
 * @author WangYanCheng
 * @version 2011-2-21
 */
public class StatementTest {
    /** 连接 */
    private Connection conn;

    /**
     * 负责PreparedStatement测评
     * @throws SQLException 抛出异常
     */
    public void preparedTest() throws SQLException {
        String[] paramValue = {"PL_YEAR", "PL_FEE"};
        String sql = "UPDATE SY_WF_VESTIGE_LOG SET MAC_ADDR='ABCDEF' WHERE DATA_FIELD=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (String str : paramValue) {
            pstmt.setString(1, str);
            pstmt.addBatch();
        }
        int[] paramArray = pstmt.executeBatch();
        System.out.println(Arrays.toString(paramArray));
    }

    /**
     * Cursor Holdability
     * @throws SQLException 抛出异常
     */
    public void cursorHoldabilitySupport() throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        System.out.println("dbMetaData.getResultSetHoldability：" + dbMetaData.getResultSetHoldability());
        System.out.println("dbMetaData.supportsResultSetHoldability："
            + dbMetaData.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT));
        System.out.println(dbMetaData.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT));
        System.out.println("ResultSet.HOLD_CURSORS_OVER_COMMIT = " + ResultSet.HOLD_CURSORS_OVER_COMMIT);
        System.out.println("ResultSet.CLOSE_CURSORS_AT_COMMIT = " + ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    /**
     * setter connection
     * @param conn Connection
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
