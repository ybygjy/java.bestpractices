package org.ybygjy.basic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleJDBCDriver {
    public Connection testGetConn() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:ORCL", "system", "dbAdmin");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null == conn) {
                throw new RuntimeException("获取连接失败");
            } else {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return conn;
    }
    public static void main(String[] args) {
        new OracleJDBCDriver().testGetConn();
    }
}
