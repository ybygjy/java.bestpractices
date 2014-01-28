package org.ybygjy.ui;

import java.io.File;
import java.sql.Connection;
import java.util.logging.Level;

import org.ybygjy.IntegrationTests;
import org.ybygjy.MessageListener;
import org.ybygjy.TestModel;
import org.ybygjy.exp.ExpInterface4DBMS;
import org.ybygjy.exp.ExpInterface4EDC;
import org.ybygjy.exp.ExpInterface4FBCM;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.FileUtils;

/**
 * 适配UI与逻辑处理
 * @author WangYanCheng
 * @version 2012-10-24
 */
public class UILogicAdapter extends IntegrationTests {
    /**原数据库*/
    private Connection srcConn;
    /**目标数据库*/
    private Connection tarConn;
    /**消息传递对象*/
    private MessageListener messageListener;
    /**文件目录实例*/
    private File dirFileInst;
    /**
     * Constructor
     * @param srcConnStr
     * @param tarConnStr
     * @param mlInst
     */
    public UILogicAdapter(String srcConnStr, String tarConnStr, MessageListener mlInst, String dirFile) {
        this.srcConn = DBUtils.createConn4Oracle(srcConnStr);
        if (null == this.srcConn) {
            logger.log(Level.WARNING, "数据库连接失败，请检查指定的连接串\r\n".concat(srcConnStr));
        }
        this.tarConn = DBUtils.createConn4Oracle(tarConnStr);
        if (null == this.tarConn) {
            logger.log(Level.WARNING, "数据库连接失败，请检查指定的连接串\r\n".concat(tarConnStr));
        }
        this.messageListener = mlInst;
        this.dirFileInst = new File(dirFile);
        super.startUp();
    }

    @Override
    protected Connection getSrcConnection() {
        return this.srcConn;
    }

    @Override
    protected Connection getTargetConnection() {
        return this.tarConn;
    }

    @Override
    public void doWork() {
        this.messageListener.beforeListener();
        super.doWork();
        super.tearDown();
        this.messageListener.afterListener();
    }

    @Override
    protected TestModel getTestModel() {
        return TestModel.ORA_ORA;
    }

    @Override
    protected String[] getTableName() {
        return FileUtils.fetchFileName(dirFileInst);
    }

    @Override
    protected String getModuleName() {
        return FileUtils.getFileName(dirFileInst);
    }

    @Override
    protected boolean beforeMigration(String moduleName) {
        super.beforeMigration(moduleName);
        if ("DBMS".equals(moduleName)) {
            new ExpInterface4DBMS(testEnvInst).beforeMigration();
        } else if ("FBCM".equals(moduleName)) {
            new ExpInterface4FBCM(testEnvInst).beforeMigration();
        } else if ("EDC".equals(moduleName)) {
            new ExpInterface4EDC(testEnvInst).beforeMigration();
        }
        return true;
    }

    @Override
    protected void afterMigration(String moduleName) {
        super.afterMigration(moduleName);
        // TODO 新的需求，在某个具体模块迁移完成后要有其它业务级别动作
        if ("DBMS".equals(moduleName)) {
            new ExpInterface4DBMS(testEnvInst).afterMigration();
        } else if ("FBCM".equals(moduleName)) {
            new ExpInterface4FBCM(testEnvInst).afterMigration();
        } else if ("EDC".equals(moduleName)) {
            new ExpInterface4EDC(testEnvInst).afterMigration();
        }
    }

    /** 数据对象{模块,表名称目录} */
    private String[][] dataCollect = {/*
                                       * {"BMS", "C:\\KettleLog\\BMS"}, {"RMS",
                                       * "C:\\KettleLog\\RMS"}
                                       */{ "FBCM", "C:\\KettleLog\\ERROR" } };
}
