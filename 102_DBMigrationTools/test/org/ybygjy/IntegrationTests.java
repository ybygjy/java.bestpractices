package org.ybygjy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ybygjy.ctx.MigrationContextFactory;
import org.ybygjy.exec.SqlExecutorListener;
import org.ybygjy.logger.LoggerFactory;
import org.ybygjy.meta.model.TableMeta;
import org.ybygjy.sql.model.SqlModel;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.SysConstants;
import org.ybygjy.util.TableUtils;

/**
 * 集成测试
 * @author WangYanCheng
 * @version 2012-10-16
 */
public class IntegrationTests {
    /** 与Oracle数据库的连接 */
    private Connection targetConnection;
    /** 与SQLServer数据库的连接 */
    private Connection srcConnection;
    /** 测试环境组件资源实例 */
    protected TestEnv testEnvInst;
    /** 日志 */
    protected Logger logger;
    /** 模块名称*/
    private String moduleName;
    @Before
    public void startUp() {
        initLogger();
        srcConnection = getSrcConnection();
        targetConnection = getTargetConnection();
        logger = LoggerFactory.getInstance().getLogger(getClass().getName());
        doBuildEnv();
    }

    /**
     * 构建测试环境
     */
    protected void doBuildEnv() {
        testEnvInst = new TestEnv();
        switch (getTestModel()) {
            case MSSQL_ORA:
                testEnvInst.buildMSSql2Oracle(srcConnection, targetConnection);
                break;
            case ORA_ORA:
                testEnvInst.buildOracle2Oracle(srcConnection, targetConnection);
                break;
        }
    }

    @Test
    public void doWork() {
        logger.info("开始普通数据的迁移");
        List<TableMeta> tableMetaes = new ArrayList<TableMeta>();
        beforeMigration(getModuleName());
        for (String tableName : getTableName()) {
            logger.info("开始迁移表：".concat(tableName));
            TableMeta tableMeta = getCommonTableMeta(tableName);
            if (null == tableMeta) {
                continue;
            }
            if (beforeMigrationTable(getModuleName(), tableMeta)) {
                doMigrationCommonData(tableMeta);
            }
            afterMigrationTable(getModuleName(), tableMeta);
            if (tableMeta.hasSpecialType()) {
                tableMetaes.add(tableMeta);
            }
            logger.info("完成迁移表：".concat(tableName));
        }
        showRawInsertFailInfo();
        showConstErrorInfo();
        logger.info("完成普通数据的迁移");
        logger.info("开始特殊数据的迁移");
        showSPTypeInfo(tableMetaes);
        for (TableMeta tableMeta : tableMetaes) {
            logger.info("开始特殊数据迁移表：".concat(tableMeta.getTableName()));
            doMigrationSpecialData(tableMeta);
            logger.info("完成特殊数据迁移表：".concat(tableMeta.getTableName()));
        }
        afterMigration(getModuleName());
        logger.info("完成特殊数据的迁移");
    }

    /**
     * 测试所用的表
     * @return tableName 表名称
     */
    protected String[] getTableName() {
        return new String[] { "EDC_MESSAGE" };
    }

    protected Connection getSrcConnection() {
        return DBUtils.createConn4MSSql(SysConstants.DB_URL_SQLSERVER);
    }

    protected Connection getTargetConnection() {
        return DBUtils.createConn4Oracle(SysConstants.DB_URL_ORACLE);
    }

    protected TestModel getTestModel() {
        return TestModel.MSSQL_ORA;
    }
    
    /**
     * @return the moduleName
     */
    protected String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName the moduleName to set
     */
    protected void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    /**
     * 表数据迁移前
     * @param moduleName 模块编码
     * @param tableMeta 表对象
     * @return rtnFlag true/false
     */
    protected boolean beforeMigrationTable(String moduleName, TableMeta tableMeta) {
        String tableName = tableMeta.getTargetTableName();
        boolean rtnFlag = true;
        logger.info("禁用表的触发器：".concat(tableName));
        testEnvInst.getTarMetaMgr().disableTableTriggers(tableName);
        logger.info("禁用表的触发器完成：".concat(tableName));
        logger.info("清理表：".concat(tableName));
        rtnFlag = SysConstants.TABLE_TRUNCATE_SUCCESS == TableUtils.truncateTable(targetConnection, tableName);
        logger.info("清理完成：".concat(tableName));
        logger.info("禁用表关联的约束：".concat(tableName));
        testEnvInst.getTarMetaMgr().disableTableConstraint(tableName);
        logger.info("禁用表关联的约束完成：".concat(tableName));
        return rtnFlag;
    }
    /**
     * 表迁移完成后
     * @param moduleName 模块名称
     * @param tableMeta 表对象
     */
    protected void afterMigrationTable(String moduleName, TableMeta tableMeta) {
        String tableName = tableMeta.getTargetTableName();
        logger.info("启用表的触发器：".concat(tableName));
        testEnvInst.getTarMetaMgr().enableTableTriggers(tableName);
        logger.info("启用表的触发器完成：".concat(tableName));
        logger.info("启用表关联的约束：".concat(tableName));
        testEnvInst.getTarMetaMgr().enableTableConstraint(tableName);
        logger.info("启用表关联的约束完成：".concat(tableName));
        logger.info("重置与表关联的序列：".concat(tableName));
        testEnvInst.getTarMetaMgr().resetTableSequence(tableName);
        logger.info("重置与表关联的表序列完成：".concat(tableName));
    }
    /**
     * 普通数据迁移之前
     * @param moduleName 模块名称
     * @return rtnFlag true/false
     */
    protected boolean beforeMigration(String moduleName) {
        return true;
    }

    /**
     * 普通数据迁移之后
     * @param moduleName 模块名称
     */
    protected void afterMigration(String moduleName) {
    }

    /**
     * 迁移普通类型数据
     * @param tableMeta {@link TableMeta}
     */
    private void doMigrationCommonData(TableMeta tableMeta) {
        final SqlModel tarSMInst = testEnvInst.getTarSqlMgr().buildInsSQL(tableMeta);
        testEnvInst.getSrcSqlExe().addListener(new SqlExecutorListener() {
            @Override
            public void afterSpecialTypeQuery(SqlModel sqlModel, ResultSet rs) {
            }

            @Override
            public void afterQuery(SqlModel smInst, List<Map<String, Object>> dataMap) {
                testEnvInst.getTarSqlExe().executeInsert(tarSMInst, dataMap);
            }
        });
        testEnvInst.getSrcSqlExe().executeQuery(testEnvInst.getSrcSqlMgr().buildQrySQL(tableMeta));
    }

    /**
     * 迁移特殊类型数据
     * @param tableMeta {@link TableMeta}
     */
    private void doMigrationSpecialData(TableMeta tableMeta) {
        final SqlModel stypesm4Ora = testEnvInst.getTarSqlMgr().buildInsertClobSQL(tableMeta);
        if (stypesm4Ora == null) {
            return;
        }
        testEnvInst.getSrcSTypeExe().addListener(new SqlExecutorListener() {
            @Override
            public void afterSpecialTypeQuery(SqlModel sqlModel, ResultSet rs) {
                testEnvInst.getTarSTypeExe().executeSpecialTypeInsert(sqlModel, stypesm4Ora, rs);
            }

            @Override
            public void afterQuery(SqlModel smInst, List<Map<String, Object>> dataMap) {
            }
        });
        testEnvInst.getSrcSTypeExe().executeQuerySpecialType(
            testEnvInst.getSrcSqlMgr().buildQryClobSQL(tableMeta));
    }

    /**
     * 打印特殊类型表信息
     * @param tableMetas {@link TableMeta}集
     */
    private void showSPTypeInfo(List<TableMeta> tableMetas) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        for (TableMeta tableMeta : tableMetas) {
            sbuf.append(tableMeta.getTableName()).append("\t");
            if (i % 10 == 0) {
                sbuf.append("\t\n");
            }
        }
        logger.warning("打印特殊类型表信息\t\n".concat(sbuf.toString()));
    }

    /**
     * 打印约束启/禁用错误信息
     */
    private void showConstErrorInfo() {
        Object tmpObj = MigrationContextFactory.getInstance().getCtx()
            .getAttribute(SysConstants.CTX_SQL_DISCONST);
        if (tmpObj != null) {
            SortedSet<String> sqlSets = (SortedSet<String>) tmpObj;
            StringBuffer sbuf = new StringBuffer();
            for (Iterator<String> iterator = sqlSets.iterator(); iterator.hasNext();) {
                sbuf.append(iterator.next()).append(";\t\n");
            }
            logger.warning("打印约束启/禁用错误信息\t\n".concat(sbuf.toString()));
        }
    }

    /**
     * 打印插入错误SQL语句
     */
    private void showRawInsertFailInfo() {
        Object tmpObj = MigrationContextFactory.getInstance().getCtx()
            .getAttribute(SysConstants.CTX_SQL_RAWINSERT_FAIL);
        if (tmpObj != null) {
            SortedSet<String> sqlSets = (SortedSet<String>) tmpObj;
            StringBuffer sbuf = new StringBuffer();
            for (Iterator<String> iterator = sqlSets.iterator(); iterator.hasNext();) {
                sbuf.append(iterator.next()).append(";\t\n");
            }
            logger.warning("打印插入错误SQL语句\t\n".concat(sbuf.toString()));
        }
    }

    /**
     * 取公共{@link TableMeta}
     * @param tableName 表名称
     * @return rtnMeta {@link TableMeta}
     */
    protected TableMeta getCommonTableMeta(String tableName) {
        TableMeta tarTMInst = testEnvInst.getTarMetaMgr().getTableMeta(tableName);
        if (null == tarTMInst) {
            logger.info("目的端缺少表：".concat(tableName));
            return null;
        }
        String bmsTableName = tableName.replaceAll("_PD", "");
        TableMeta srcTMInst = testEnvInst.getSrcMetaMgr().getTableMeta(bmsTableName);
        if (null == srcTMInst) {
            logger.info("源端缺少表：".concat(tableName));
            return null;
        }
        TableMeta commonTM = srcTMInst.consultAndRebuilt(tarTMInst);
        //TODO 拙劣的设计
        commonTM.setSrcTableName(bmsTableName);
        commonTM.setTargetTableName(tableName);
        return commonTM;
    }

    /**
     * 初始Logger
     */
    protected void initLogger() {
        try {
            Handler fileHandler = new FileHandler(getLogFile(), false);
            fileHandler.setFormatter(new SimpleFormatter());
            LoggerFactory.getInstance().addLoggerHandler(fileHandler);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取Log日志存储文件
     * @return rtnFilePath 文件名称
     */
    protected String getLogFile() {
        return "./DataMigrationLog%g.log";
    }

    @After
    public void tearDown() {
        DBUtils.close(targetConnection);
        DBUtils.close(srcConnection);
    }


}
