package org.ybygjy.basic.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

/**
 * 连接管理
 * @author WangYanCheng
 * @version 2011-2-21
 */
public class ConnectionMgr {
    /**数据源*/
    private static OracleDataSource ods;
    static {
        try {
            ods = new OracleDataSource();
            ods.setURL("jdbc:oracle:thin:leopard/leopard@192.168.0.7:1521:version");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得Connection
     * @return connInst 连接实例
     * @throws SQLException SQLException
     */
    public static Connection getConn() throws SQLException {
        return ods.getConnection();
    }

    /**
     * 关闭Connection
     * @param conn 连接实例
     */
    public static void closeConn(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
