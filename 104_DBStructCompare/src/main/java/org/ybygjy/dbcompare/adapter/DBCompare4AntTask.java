package org.ybygjy.dbcompare.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;

import org.apache.tools.ant.BuildException;
import org.ybygjy.dbcompare.DBCompare;
import org.ybygjy.dbcompare.DBEnum;
import org.ybygjy.dbcompare.DBUtils;


/**
 * Ant任务入口
 * @author WangYanCheng
 * @version 2011-10-11
 */
public class DBCompare4AntTask extends org.apache.tools.ant.Task {
    /** 数据库连接串 */
    private String dbURL;
    /** 原始用户 */
    private String srcUser;
    /** 参照用户 */
    private String targetUser;
    /** 转储文件 */
    private String restoreFilePath;
    /** 数据库类型{ORACLE\MSSQL}*/
    private String dbType;
    /** dbTypeEnum*/
    private DBEnum dbTypeEnum;
    @Override
    public void execute() throws BuildException {
		Connection conn = innerGetConn();
        if (conn == null) {
        	return;
        }
        DBCompare dbCompare = null;
        try {
            dbCompare = new DBCompare(conn, getSrcUser(), getTargetUser());
            dbCompare.setReportCtxOutput(createRestoreOutput());
            dbCompare.setDbType(dbTypeEnum);
            dbCompare.doWork();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != dbCompare) {
                if (null != dbCompare.getReportCtxOutput()) {
                    try {
                        dbCompare.getReportCtxOutput().flush();
                        dbCompare.getReportCtxOutput().close();
                    } catch (Exception e) {
System.out.println("关闭转储流失败，请联系开发人员。");
                        e.printStackTrace();
                    }
                }
            }
System.out.println("关闭连接");
            DBUtils.close(conn);
        }
System.out.println("转储文件：".concat(getRestoreFilePath()));
    }

    /**
     * create Connection
     * @param dbURL2 连接串
     * @return rtnConn rtnConn/null
     */
    private Connection innerGetConn() {
System.out.println("初始化连接");
    	Connection rtnConn= null;
    	if ("MSSQL".equals(getDbType())) {
    		dbTypeEnum = DBEnum.MSSQL;
    		rtnConn = DBUtils.createConn4MSSql(getDbURL());
    	} else {
    		dbTypeEnum = DBEnum.ORA;
    		rtnConn = DBUtils.createConn4Oracle(getDbURL());
    	}
    	if (rtnConn == null) {
    		throw new RuntimeException("初始化连接失败：\n连接串：".concat(getDbURL()));
    	}
System.out.println(dbURL + "\t" + this.srcUser + "\t" + this.targetUser);
System.out.println("初始化连接完毕");
		return rtnConn;
	}

	/**
     * 创建转储流对象
     * @return rtnOUS rtnOUS
     */
    private OutputStream createRestoreOutput() {
        if (getRestoreFilePath() == null) {
            return null;
        }
        System.out.println("开始创建转储流对象");
        File rFile = null;
        OutputStream ous = null;
        try {
            rFile = getRestoreFilePath() == null ? null : new File(getRestoreFilePath());
            if (rFile == null) {
System.out.println("创建转储文件失败(不能使用转储流)，文件地址:[" + getRestoreFilePath() + "]");
            } else {
                ous = new FileOutputStream(rFile, false);
System.out.println("完成创建转储流对象，转储文件：" + rFile.getAbsolutePath());
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ous;
    }

    public String getDbURL() {
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getSrcUser() {
        return srcUser;
    }

    public void setSrcUser(String srcUser) {
        this.srcUser = srcUser.toUpperCase();
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser.toUpperCase();
    }

    public String getRestoreFilePath() {
        return restoreFilePath;
    }

    public void setRestoreFilePath(String restoreFile) {
        this.restoreFilePath = restoreFile;
    }

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
}
