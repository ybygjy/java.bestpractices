package org.ybygjy.example;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.ybygjy.util.DBUtils;
import org.ybygjy.util.FileUtils;

/**
 * 统计表的行数
 * @author WangYanCheng
 * @version 2012-5-29
 */
public class StatisticsTableLineNumbers {
    /** 查询表的行数SQL模版 */
    private static final String QRY_ORA = "SELECT COUNT(1) CC FROM @T";
    /** 与Oracle数据库的连接 */
    private Connection conn4Ora;
    /** 与SQLServer数据库的连接 */
    private Connection conn4MSSQL;

    /**
     * 构造函数
     * @param conn4Ora
     * @param conn4MSSQL
     */
    public StatisticsTableLineNumbers(Connection conn4Ora, Connection conn4MSSQL) {
        this.conn4MSSQL = conn4MSSQL;
        this.conn4Ora = conn4Ora;
    }

    /**
     * 在Oracle数据库和SQLServer数据库分别查询给定表名称的行数并打印
     * @param tableNames 表名称集合
     */
    public void doWork(String[] tableNames) {
        for (String tableName : tableNames) {
            int oraNums = qryTableNums(conn4Ora, tableName);
            int mssqlNums = qryTableNums(conn4MSSQL, tableName);
            if (oraNums == 0) {
                continue;
            }
            System.out.println("表名称：".concat(tableName).concat("\tOracle：").concat(String.valueOf(oraNums)).concat("\tSQLServer：").concat(String.valueOf(mssqlNums)));
        }
    }
    /**
     * 查表的数据行数
     * @param conn 与数据库的连接
     * @param tableName 表名称
     * @return rtnNums 数据行数/异常情况下返回-1
     */
    public int qryTableNums(Connection conn, String tableName) {
        Statement stmt = null;
        ResultSet rs = null;
        String tmplSQL = QRY_ORA.replace("@T", tableName);
        int rtnNums = 0;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(tmplSQL);
            if (rs.next()) {
                rtnNums = rs.getInt(1);
            }
        } catch (Exception e) {
            rtnNums = -1;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnNums;
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        String url4MSSQl = "jdbc:sqlserver://192.168.0.16;instanceName=SQL2005;databaseName=angangdata;user=sa;password=11111111";
        String url4Oracle = "jdbc:oracle:thin:NSTCSA4003/6316380@192.168.3.232:1521/NSDEV";
        Connection conn4Ora = DBUtils.createConn4Oracle(url4Oracle);
        Connection conn4MSSql = DBUtils.createConn4MSSql(url4MSSQl);
        try {
            String[] tableNames = FileUtils.fetchFileName(new File("C:\\KettleLog\\FBCM"));
            StatisticsTableLineNumbers stlnInst = new StatisticsTableLineNumbers(conn4Ora, conn4MSSql);
            stlnInst.doWork(tableNames);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != conn4Ora) {
                try {
                    conn4Ora.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn4MSSql) {
                try {
                    conn4MSSql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
