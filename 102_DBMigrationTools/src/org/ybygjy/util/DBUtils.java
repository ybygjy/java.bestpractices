package org.ybygjy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.ybygjy.BusinessException;
import org.ybygjy.logger.LoggerFactory;

/**
 * 工具类
 * @author WangYanCheng
 * @version 2011-10-4
 */
public class DBUtils {
    /**SQLServer数据库驱动*/
    private static final String MS_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    /**Oracle数据库驱动*/
    private static final String ORA_DRIVER = "oracle.jdbc.driver.OracleDriver";
    /**日志实例*/
    private static Logger logger = LoggerFactory.getInstance().getLogger(DBUtils.class.getName());
    /**
     * 取Oracle数据库连接
     * @param connURL 连接串
     * @return rtnConn rtnConnection
     */
    public static Connection createConn4Oracle(String connURL) {
    	Connection conn = null;
    	try {
			Class.forName(ORA_DRIVER);
			conn = DriverManager.getConnection(connURL);
		} catch (ClassNotFoundException e) {
			logger.warning("Oracle 数据库驱动加载失败：".concat(e.getMessage()));
		} catch (SQLException e) {
		    logger.warning("Oracle 数据库连接失败：".concat(e.getMessage()));
		}
    	return conn;
    }
    /**
     * 取Oracle数据库连接
     * @param connURL 连接串
     * @return rtnConn rtnConnection
     */
    public static Connection createConn4OracleDBA(String connURL) {
        Connection conn = null;
        try {
            Class.forName(ORA_DRIVER);
            Properties prop = new Properties();
            prop.setProperty("internal_logon", "sysdba");
            conn = DriverManager.getConnection(connURL, prop);
        } catch (ClassNotFoundException e) {
            logger.warning("Oracle 数据库驱动加载失败：".concat(e.getMessage()));
        } catch (SQLException e) {
            logger.warning("Oracle 数据库连接失败：".concat(e.getMessage()));
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
        close(rs);
        close(stmt);
    }

    public static void close(ResultSet rs) {
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
            logger.warning("SQLServer 数据库驱动加载失败：".concat(cnfe.getMessage()));
        } catch (SQLException sqle) {
            logger.warning("SQLServer 数据库连接失败：".concat(sqle.getMessage()));
        }
        return conn;
    }
    /**
     * 提取结果集数据
     * @param rs {@link ResultSet}
     * @throws SQLException
     */
    public static synchronized List<Map<String, Object>> extractData(ResultSet rs) throws SQLException {
        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
        ResultSetMetaData rsmdInst = rs.getMetaData();
        int cols= rsmdInst.getColumnCount();
        ColumnInfo[] columns = new ColumnInfo[cols];
        for (int i = 0; i < cols; i ++) {
            columns[i] = new ColumnInfo(rsmdInst.getColumnName((i + 1)), rsmdInst.getColumnType((i + 1)));
        }
        while (rs.next()) {
            Map<String, Object> valueEntity = new HashMap<String, Object>();
            for (ColumnInfo column : columns) {
                valueEntity.put(column.columnName, getColumnValue(rs, column));
            }
            rtnList.add(valueEntity);
        }
        return rtnList;
    }
    /**
     * 取结果集中给定列的值
     * @param rs {@link ResultSet}
     * @param colInfo {@link ColumnInfo}
     * @return rtnObj 值
     * @throws SQLException
     */
    public static synchronized Object getColumnValue(ResultSet rs, ColumnInfo colInfo) throws SQLException {
        Object rtnObj = null;
        if (Types.CLOB == colInfo.columnType) {
            Clob clob = rs.getClob(colInfo.columnName);
            BufferedReader readerBuff = new BufferedReader(clob.getCharacterStream());
            StringBuilder sbud = new StringBuilder();
            String tmpStr = null;
            try {
                while ((tmpStr = readerBuff.readLine())!=null) {
                    sbud.append(tmpStr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    readerBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            rtnObj = sbud.toString();
        } else if (Types.BLOB == colInfo.columnType) {
            Blob blob = rs.getBlob(colInfo.columnName);
            try {
                rtnObj = FileUtils.restoreInputStream("C:\\temp", String.valueOf((int)(Math.random()*100)), blob.getBinaryStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rtnObj == null ? rs.getObject(colInfo.columnName) : rtnObj;
    }
    /**
     * 列信息
     * @author WangYanCheng
     * @version 2012-8-30
     */
    static class ColumnInfo {
        /**列名称*/
        private String columnName;
        /**列类型*/
        private int columnType;
        /**
         * Constructor
         * @param columnName 列名称
         * @param columnType 列类型
         */
        public ColumnInfo(String columnName, int columnType) {
            this.columnName = columnName;
            this.columnType = columnType;
        }
    }
    /**
     * 数据当前连接的Schema
     * @param conn {@link Connection}
     * @return rtnSchema/null
     */
    public static synchronized String getSchema(Connection conn) {
        String rtnSchema = null;
        try {
            DatabaseMetaData dmdInst = conn.getMetaData();
            rtnSchema = dmdInst.getUserName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rtnSchema;
    }

    /**
     * 测试数据库的连接
     * @param dieInst {@link DBConfEntry}
     * @return rtnFlag {true;false}
     * @throws BusinessException 业务级异常
     */
    public static synchronized boolean testConnection(DBConfEntry dieInst) throws BusinessException {
        boolean rtnFlag = true;
        Connection conn = null;
        try {
            conn = createConnection(dieInst.getDbType().getDriver(), dieInst.getConnUrl(), dieInst.getUserName(), dieInst.getPassWord());
            dieInst.setVaild(rtnFlag = (conn != null));
        } catch (SQLException e) {
            //逻辑级别异常
            throw new BusinessException("数据库连接失败", e);
        }
        return rtnFlag;
    }
    /**
     * 创建数据库的连接
     * @param driver 驱动
     * @param connUrl 连接串
     * @param userName 用户名
     * @param password 密码
     * @return conn/null
     * @throws SQLException {@link SQLException}
     */
    public static synchronized Connection createConnection(String driver, String connUrl, String userName, String password) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connUrl, userName, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return conn;
    }
}