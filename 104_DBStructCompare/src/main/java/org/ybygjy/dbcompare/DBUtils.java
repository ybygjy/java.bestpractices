package org.ybygjy.dbcompare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

/**
 * 工具类
 * @author WangYanCheng
 * @version 2011-10-4
 */
public class DBUtils {
    /** 数据源 */
    private static DataSource ds;
    /** 初始化标记 */
    private static boolean isInit = false;
    /**SQLServer数据库驱动*/
    private static final String MS_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    /**Oracle数据库驱动*/
    private static final String ORA_DRIVER = "oracle.jdbc.driver.OracleDriver";
    /**
     * 取Oracle数据库连接
     * @param connURL 连接串
     * @return rtnConn rtnConnection
     */
    public static Connection createConn4Oracle(String connURL) {
    	Connection conn = null;
    	try {
			Class.forName(ORA_DRIVER);
			Properties prop = new Properties();
			prop.setProperty("internal_logon", "sysdba");
			conn = DriverManager.getConnection(connURL, prop);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Oracle 数据库驱动加载失败", e.fillInStackTrace());
		} catch (SQLException e) {
			throw new RuntimeException("Oracle 数据库连接失败", e.fillInStackTrace());
		}
    	return conn;
    }
    /**
     * 取MSSql数据库连接
     * @param connURL 连接串
     * @return rtnConn rtnConnection
     */
    public static Connection createConn4MSSql(String connURL) {
    	Connection conn = null;
    	try {
    		Class.forName(MS_DRIVER);
    		conn = DriverManager.getConnection(connURL);
    	} catch(ClassNotFoundException cnfe) {
    		throw new RuntimeException("SQLServer 数据库驱动加载失败", cnfe.fillInStackTrace());
    	} catch (SQLException sqle) {
    		throw new RuntimeException("SQLServer 数据库连接失败", sqle.fillInStackTrace());
    	}
    	return conn;
    }

    /**
     * 创建SQL语句执行实例
     * @param conn 连接对象
     * @return rtnStmt rtnStmt
     * @throws SQLException 抛出异常做Log
     */
    public static Statement createStmt(Connection conn) throws SQLException {
        return conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    public static void close(ResultSet rs, Statement stmt) {
        closeResultSet(rs);
        close(stmt);
    }

    public static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stmt) {
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接
     * @param conn conn
     */
    public static void close(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    /**
     * 取RS的列编码，以数组方式返回
     * @param rs {@link ResultSet}
     * @return rtnArr 列数组
     * @throws SQLException SQLException
     */
    public static String[] getRSColumn(ResultSet rs) throws SQLException {
        if (null == rs) {
            return null;
        }
        int count = rs.getMetaData().getColumnCount();
        String[] rtnArr = new String[count];
        for (int i = 1; i <= count; i++) {
            rtnArr[i - 1] = rs.getMetaData().getColumnName(i);
        }
        return rtnArr;
    }

    public static void main(String[] args) {
        Connection conn = null;
        try {
//            conn = DBUtils.createConn4Oracle("jdbc:oracle:thin:sys/Syj129@192.168.0.74:1521/nstest");
        	conn = DBUtils.createConn4MSSql("jdbc:sqlserver://192.168.0.132:1433;databaseName=MASTER;user=sa;password=syj");
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn);
        }
    }
}
