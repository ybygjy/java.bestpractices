package org.ybygjy.sql.impl;

import java.sql.Connection;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ybygjy.meta.impl.MetaMgrImpl4Oracle;
import org.ybygjy.meta.model.TableMeta;
import org.ybygjy.sql.SqlMgr;
import org.ybygjy.sql.model.SqlModel;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.SysConstants;

/**
 * {@link SqlMgrImpl4Oracle} ≤‚ ‘”√¿˝
 * @author WangYanCheng
 * @version 2012-10-16
 */
public class SqlMgrImpl4OracleTest {
    /** {@link Connection}*/
    private Connection conn4Ora;
    /** {@link SqlMgr}*/
    private SqlMgr sqlMgr;
    /** {@link TableMeta}*/
    private TableMeta tableMeta;
    /** ±Ì√˚≥∆*/
    private String tableName;
    /** {@link SqlModel}*/
    private SqlModel sqlModel;
    @Before
    public void setUp() throws Exception {
        this.tableName = "EDC_MESSAGE";
        this.conn4Ora = DBUtils.createConn4Oracle(SysConstants.DB_URL_ORACLE);
        this.sqlMgr = new SqlMgrImpl4Oracle();
        this.tableMeta = new MetaMgrImpl4Oracle(conn4Ora).getTableMeta(tableName);
    }

    @After
    public void tearDown() throws Exception {
        DBUtils.close(conn4Ora);
    }

    @Test
    public void testBuildQrySQLTableMeta() {
        sqlModel = sqlMgr.buildQrySQL(tableMeta);
        Assert.assertNotNull(sqlModel);
        System.out.println(sqlModel.getSqlStmt());
    }

    @Test
    public void testBuildInsSQLTableMeta() {
        sqlModel = sqlMgr.buildInsSQL(tableMeta);
        Assert.assertNotNull(sqlModel);
        System.out.println(sqlModel.getSqlStmt());
    }

    @Test
    public void testBuildQryClobSQL() {
        sqlModel = sqlMgr.buildQryClobSQL(tableMeta);
        Assert.assertNotNull(sqlModel);
        System.out.println(sqlModel.getSqlStmt());
    }

    @Test
    public void testBuildInsertClobSQL() {
        sqlModel = sqlMgr.buildInsertClobSQL(tableMeta);
        Assert.assertNotNull(sqlModel);
        System.out.println(sqlModel.getSqlStmt());
    }

}
