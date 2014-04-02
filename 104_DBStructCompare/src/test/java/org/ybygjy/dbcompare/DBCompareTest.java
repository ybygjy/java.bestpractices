package org.ybygjy.dbcompare;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ybygjy.dbcompare.DBCompare;
import org.ybygjy.dbcompare.DBEnum;
import org.ybygjy.dbcompare.DBUtils;

/**
 * 单元测试用例
 * @author WangYanCheng
 * @version 2012-5-10
 */
public class DBCompareTest {
	private String oracleConnURL;
	private String mssqlConnURL;
	private String srcUser;
	private String tarUser;
	private Connection conn;
	private static String connUrl4Ora = "jdbc:oracle:thin:sys/Syj129@192.168.0.74:1521/NSTEST";
	@Before
	public void setUp() throws Exception {
		srcUser = "NSTCSA2442";
		tarUser = "NSTCSA2922";
	}

	@After
	public void tearDown() throws Exception {
		DBUtils.close(conn);
	}
	@Test
	public void testDoWork4Oracle() {
		//oracleConnURL = "jdbc:oracle:thin:sys/Syj129@192.168.0.74:1521/NSTEST";
		oracleConnURL = "jdbc:oracle:thin:sys/Syj129@192.168.0.74:1521/NSTEST";
		conn = DBUtils.createConn4Oracle(oracleConnURL);
		this.testDoWork(DBEnum.ORA);
	}
	@Test
	public void testDoWork4MSSql() {
//		mssqlConnURL = "jdbc:sqlserver://192.168.0.132:1433;databaseName=MASTER;user=sa;password=syj";
//		srcUser = "NSTEST45";
//		tarUser = "NSTEST46";
		mssqlConnURL = "jdbc:sqlserver://192.168.0.111:1533;databaseName=n6_mssql;user=sa;password=11111111";
		srcUser = "n6_mssql";
		tarUser = "n6_30";
		srcUser = srcUser.toUpperCase();
		tarUser = tarUser.toUpperCase();
		conn = DBUtils.createConn4MSSql(mssqlConnURL);
		this.testDoWork(DBEnum.MSSQL);
	}
	public void testDoWork(DBEnum dbType) {
		FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(createFile("C:\\DBCompareLog"));
            DBCompare dbCInst = new DBCompare(conn, srcUser, tarUser);
            dbCInst.setReportCtxOutput(fos);
            dbCInst.setDbType(dbType);
            dbCInst.doWork();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
	}
    /**
     * 创建文件
     * @param fileFullName 文件全路径名
     * @return rtnFile rtnFile
     * @throws IOException IOException
     */
    public static File createFile(String fileFullName) throws IOException {
        File file = new File(fileFullName);
        if (file.exists()) {
            file = new File(fileFullName.concat(String.valueOf((int) (Math.random() * 100))));
        }
        file.createNewFile();
        return file;
    }
}
