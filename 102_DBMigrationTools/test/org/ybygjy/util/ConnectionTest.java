package org.ybygjy.util;

import java.sql.Connection;

import org.junit.Test;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.SysConstants;

/**
 * 测试数据库连接
 * @author WangYanCheng
 * @version 2012-10-15
 */
public class ConnectionTest {
    @Test
    public void conn4Oracle() {
        String[] urls = { SysConstants.DB_URL_ORACLE };
        for (String url : urls) {
            connOracle(url);
        }
    }

    @Test
    public void conn4MSSql() {
        String[] urls = {
                "jdbc:sqlserver://192.168.1.138:1433;databaseName=NSDEV151;user=NSDEV151;password=800224",
                "jdbc:sqlserver://192.168.0.16;databaseName=nsag;user=nsag;password=11111111;instanceName=sql2005",
                "jdbc:sqlserver://192.168.1.138:1433;databaseName=NSDEV169;user=NSDEV169;password=186958",
                SysConstants.DB_URL_SQLSERVER };
        for (String url : urls) {
            connMSSql(url);
        }
    }

    /**
     * 连接SQL Server测试
     * @param url 数据库地址
     */
    private void connMSSql(String url) {
        Connection conn = null;
        try {
            conn = DBUtils.createConn4MSSql(url);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (null != conn) {
                System.out.println("地址->".concat(url).concat("连接成功！"));
                DBUtils.close(conn);
            } else {
                System.out.println("地址->".concat(url).concat("连接失败！"));
            }
        }
    }

    /**
     * 连接Oracle地址
     * @param url 数据库地址
     */
    private void connOracle(String url) {
        Connection conn = null;
        try {
            conn = DBUtils.createConn4Oracle(url);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (null != conn) {
                System.out.println("地址->".concat(url).concat("连接成功！"));
                DBUtils.close(conn);
            } else {
                System.out.println("地址->".concat(url).concat("连接成功！"));
            }
        }
    }
}
