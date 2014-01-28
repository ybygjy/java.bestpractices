package org.ybygjy.util;
/**
 * 定义数据库类型
 * @author WangYanCheng
 * @version 2012-11-14
 */
public enum DB_TYPE {
    /** Oracle*/
    ORACLE(11, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/SID"),
    /** SQLServer*/
    MSSQL(22, "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://127.0.0.1;databaseName=TEST;instanceName=SID"),
    /**未知类型*/
    UNKNOW(1000, "", "");
    private int value;
    private String driverClass;
    private String connTmpl;
    private DB_TYPE(int value, String driverClass, String connTmpl) {
        this.value = value;
        this.driverClass = driverClass;
        this.connTmpl = connTmpl;
    }
    public static DB_TYPE getType(int value) {
        for (DB_TYPE dt : DB_TYPE.values()) {
            if (dt.value == value) {
                return dt;
            }
        }
        return UNKNOW;
    }
    /**
     * 依据给定字符串确定数据库类型
     * @param value 字符串
     * @return dbType或{@link DB_TYPE#UNKNOW}
     */
    public static DB_TYPE getTypeByDriver(String value) {
        for (DB_TYPE dt : DB_TYPE.values()) {
            if (dt.getDriver().equals(value)) {
                return dt;
            }
        }
        return UNKNOW;
    }

    public static DB_TYPE getTypeByName(String value) {
        for (DB_TYPE dt : DB_TYPE.values()) {
            if (dt.name().equals(value)) {
                return dt;
            }
        }
        return UNKNOW;
    }
    /**
     * 数据库驱动描述
     * @return driverClass
     */
    public String getDriver() {
        return this.driverClass;
    }
    /**
     * 与数据库的连接串模版
     * @return connTmpl
     */
    public String getConnTmpl() {
        return this.connTmpl;
    }
}
