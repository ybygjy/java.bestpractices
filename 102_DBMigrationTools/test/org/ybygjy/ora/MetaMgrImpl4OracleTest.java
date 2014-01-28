package org.ybygjy.ora;

import java.sql.Connection;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ybygjy.meta.MetaMgr;
import org.ybygjy.meta.impl.MetaMgrImpl4Oracle;
import org.ybygjy.util.DBUtils;

/**
 * {@link MetaMgrImpl4Oracle} 测试用例
 * @author WangYanCheng
 * @version 2012-10-19
 */
public class MetaMgrImpl4OracleTest {
    /**与数据库的连接*/
    private Connection conn;
    /** 被测试实例*/
    private MetaMgr mmInst;
    /** 测试用表*/
    private String tableName;
    private String DB_URL_ORACLE = "jdbc:oracle:thin:NSTCSA3381/000800@192.168.0.143:1521/NSDEV";
    @Before
    public void setUp() throws Exception {
        conn = DBUtils.createConn4Oracle(DB_URL_ORACLE);
        mmInst = new MetaMgrImpl4Oracle(conn);
        tableName = "BMS_TX";
    }
    @After
    public void tearDown() throws Exception {
        DBUtils.close(conn);
    }

    @Test
    public void testDisableTableTriggers() {
        Assert.assertTrue(mmInst.disableTableTriggers(tableName));
    }

    @Test
    public void testEnableTableTriggers() {
        Assert.assertTrue(mmInst.enableTableTriggers(tableName));
    }

    @Test
    public void testResetTableSequence() {
        mmInst.resetTableSequence(tableName);
    }
}
