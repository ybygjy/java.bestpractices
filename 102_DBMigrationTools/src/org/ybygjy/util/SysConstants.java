package org.ybygjy.util;


/**
 * 系统常量
 * @author WangYanCheng
 * @version 2012-8-29
 */
public class SysConstants {
    /**默认序列值_重置序列模块会使用*/
    public static final int DB_SEQ_DEFVAL= 10000000;
    /**SQLServer数据库连接串*/
    public static final String DB_URL_SQLSERVER = "jdbc:sqlserver://192.168.0.16;databaseName=angangdata;user=nsag;password=11111111;instanceName=sql2005";
    /**Oracle数据库连接串*/
    public static final String DB_URL_ORACLE = "jdbc:oracle:thin:NSTCSA3542/747209@192.168.0.143:1521/NSDEV";
    //public static final String DB_URL_ORACLE = "jdbc:oracle:thin:NSTCSA1421/475656@192.168.0.143:1521/NSDEV";
    /**上下文变量_禁用约束失败SQL*/
    public static final String CTX_SQL_DISCONST="CTX_5001";
    /**上下文变量_插入失败SQL*/
    public static final String CTX_SQL_RAWINSERT_FAIL="CTX_2001";
    /**清理表失败*/
    public static final int TABLE_TRUNCATE_FAILURE=-1;
    /**清理表成功*/
    public static final int TABLE_TRUNCATE_SUCCESS=0;
    /**最大缓存数据条目阀值*/
    public static final int MAX_CACHEITEM = 5000;
}
