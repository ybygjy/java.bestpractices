package org.ybygjy.exp;

import org.ybygjy.TestEnv;

/**
 * 信贷模块相关
 * @author WangYanCheng
 * @version 2012-10-22
 */
public class ExpInterface4FBCM extends AbstractExpInterface {
    
    public ExpInterface4FBCM(TestEnv testEnvInst) {
        super(testEnvInst);
    }
    @Override
    public void beforeMigration() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void afterMigration() {
        //流贷：表外利息账户未迁移
        testEnvInst.getTarSqlExe().executeSQL("UPDATE FB_ACCOUNTS A SET A.ACCOUNT_TYPE = 'OUTER.INTREST' WHERE A.ACCOUNT_TYPE = 'OUTER_INTREST'");
        testEnvInst.getTarSqlExe().executeSQL("UPDATE FB_CONTRACTS B   SET B.PAYRATEPROPORTION = 100 WHERE B.BID IN (SELECT BID FROM FB_BUSINESS A WHERE A.BIZ_TYPE = '105') AND B.PAYRATEPROPORTION IS NULL");
        testEnvInst.getTarSqlExe().executeSQL("INSERT INTO FB_ACCOUNTS_TYPE(ID,BIZ_TYPE,TYPE_CODE,TYPE_NAME,IS_SHOW,CAN_EMPTY,CAN_EDIT,SHOW_ORDER) SELECT FB_ACCOUNTS_TYPE_SEQ.NEXTVAL,'104','CREDIT.OVERDUE','逾期本金账',1,1,0,1 FROM DUAL WHERE NOT EXISTS (SELECT 0 FROM FB_ACCOUNTS_TYPE WHERE BIZ_TYPE = '104' AND TYPE_CODE = 'CREDIT.OVERDUE')");
        updateRel4RMS();
        testEnvInst.getTarSqlExe().executeSQL("UPDATE FB_CHARGE_RATES A SET A.CHARGE_ID = A.CHARGE_TYPE");
        testEnvInst.getTarSqlExe().executeSQL("UPDATE FB_CHARGE_LOG A SET A.CHARGE_ID = A.CHARGE_TYPE WHERE A.CHARGE_ID IS NULL");
        testEnvInst.getTarSqlExe().executeSQL("INSERT INTO FB_ACCOUNTS SELECT A.BID, 'CREDIT.SURETY', B.ACCOUNT_NO, B.ISKM FROM FB_BUSINESS A, FB_ACCOUNTS B WHERE A.BID = B.BID AND A.BIZ_TYPE = '107' AND B.ACCOUNT_TYPE = 'CREDIT' AND NOT EXISTS (SELECT 0 FROM FB_ACCOUNTS C WHERE C.BID = A.BID AND C.ACCOUNT_TYPE = 'CREDIT.SURETY')");
        testEnvInst.getTarSqlExe().executeSQL("INSERT INTO FB_ACCOUNTS SELECT A.BID, 'EXPEND', B.ACCOUNT_NO, B.ISKM FROM FB_BUSINESS A, FB_ACCOUNTS B WHERE A.BID = B.BID AND A.BIZ_TYPE = '101' AND B.ACCOUNT_TYPE = 'DEPOSIT' AND NOT EXISTS (SELECT 0 FROM FB_ACCOUNTS C WHERE C.BID = A.BID AND C.ACCOUNT_TYPE = 'EXPEND')");
        testEnvInst.getTarSqlExe().executeSQL("INSERT INTO FB_ACCOUNTS SELECT A.BID, 'EXPEND', B.ACCOUNT_NO, B.ISKM FROM FB_BUSINESS A, FB_ACCOUNTS B WHERE A.BID = B.BID AND A.BIZ_TYPE = '104' AND B.ACCOUNT_TYPE = 'DEPOSIT' AND NOT EXISTS (SELECT 0 FROM FB_ACCOUNTS C WHERE C.BID = A.BID AND C.ACCOUNT_TYPE = 'EXPEND')");
        testEnvInst.getTarSqlExe().executeSQL("UPDATE FB_ACCOUNTS A SET A.ACCOUNT_TYPE = 'OUTER.INTREST' WHERE A.ACCOUNT_TYPE = 'OUTER_INTREST'");
        testEnvInst.getTarSqlExe().executeSQL("UPDATE FB_ACCOUNTS A SET A.ACCOUNT_TYPE = 'BUSSINESS.ASSETS' WHERE A.ACCOUNT_TYPE = 'CREDIT' AND EXISTS (SELECT 0 FROM FB_BUSINESS B WHERE B.BID = A.BID AND B.BIZ_TYPE = '104')");
        testEnvInst.getTarSqlExe().executeSQL("INSERT INTO FB_ACCOUNTS_TYPE (ID, BIZ_TYPE, TYPE_CODE, TYPE_NAME, IS_SHOW, CAN_EMPTY, CAN_EDIT, SHOW_ORDER) SELECT FB_ACCOUNTS_TYPE_SEQ.NEXTVAL, '104', 'CREDIT.OVERDUE', '逾期本金账', 1, 1, 0, 1 FROM DUAL WHERE NOT EXISTS (SELECT 0 FROM FB_ACCOUNTS_TYPE WHERE BIZ_TYPE = '104' AND TYPE_CODE = 'CREDIT.OVERDUE')");
        testEnvInst.getTarSqlExe().executeSQL("INSERT INTO FB_ACCOUNTS SELECT A.BID, 'EXPEND', B.ACCOUNT_NO, B.ISKM FROM FB_BUSINESS A, FB_ACCOUNTS B WHERE A.BID = B.BID AND A.BIZ_TYPE = '102' AND B.ACCOUNT_TYPE = 'DEPOSIT' AND NOT EXISTS (SELECT 0 FROM FB_ACCOUNTS C WHERE C.BID = A.BID AND C.ACCOUNT_TYPE = 'EXPEND')");
        testEnvInst.getTarSqlExe().executeSQL("UPDATE FB_ACCOUNTS A SET A.ACCOUNT_TYPE = 'OUTER.INTREST' WHERE A.ACCOUNT_TYPE = 'OUTER_INTREST'");
        testEnvInst.getTarSqlExe().executeSQL("INSERT INTO FB_ACCOUNTS SELECT A.BID, 'CREDIT.SURETY', B.ACCOUNT_NO, B.ISKM FROM FB_BUSINESS A, FB_ACCOUNTS B WHERE A.BID = B.BID AND A.BIZ_TYPE = '107' AND B.ACCOUNT_TYPE = 'CREDIT' AND NOT EXISTS (SELECT 0 FROM FB_ACCOUNTS C WHERE C.BID = A.BID AND C.ACCOUNT_TYPE = 'CREDIT.SURETY')");
        transformRateMoth2Year();
    }

    /**
     * 月利率转换成年利率
     */
    private void transformRateMoth2Year() {
        String[] tmplSql = new String[]{
            "UPDATE FB_INTREST_LOG A SET A.RATE = ROUND(A.RATE * 12 / 10, 5)",
            "UPDATE FB_SPECIAL_INTREST_LOG A SET A.RATE = ROUND(A.RATE * 12 / 10, 5)",
            "UPDATE FB_INTREST_RATES A SET A.RATE = ROUND(A.RATE * 12 / 10, 5) WHERE A.RATE_TYPE='00'",
            "UPDATE FB_PLANS A SET A.RATE = ROUND(A.RATE * 12 / 10, 5)",
            "UPDATE FB_WITHHOLDING_LOG A SET A.RATE = ROUND(A.RATE * 12 / 10, 5)",
            "UPDATE FB_PLAN_HISTORY A SET A.RATE = ROUND(A.RATE * 12 / 10, 5)",
            "UPDATE FB_SPECIAL_HOLDING_LOG A SET A.RATE = ROUND(A.RATE * 12 / 10, 5)"
        };
        testEnvInst.getTarSqlExe().executeSQL(tmplSql);
    }
    /**
     * 信贷_迁移后_与RMS费用的关联
     */
    private void updateRel4RMS() {
        String[] tmplSql = new String[]{
        "insert into RMS_CT_FEE_TYPE (ID, NO, NAME, FEE_DIR, INTR_BASE, INTR_BASE_CT, INTR_BASE_OTHER, INTR_TYPE, CREATE_TIME, LAST_UPDATE_TIME) values (1, '1', '营业税', '2', 1, 2, null, 1, to_date('22-10-2012 10:18:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-10-2012 10:18:46', 'dd-mm-yyyy hh24:mi:ss'))",
        "insert into RMS_CT_FEE_TYPE (ID, NO, NAME, FEE_DIR, INTR_BASE, INTR_BASE_CT, INTR_BASE_OTHER, INTR_TYPE, CREATE_TIME, LAST_UPDATE_TIME) values (2, '2', '城建税', '2', 1, 2, null, 1, to_date('22-10-2012 10:19:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-10-2012 10:19:12', 'dd-mm-yyyy hh24:mi:ss'))",
        "insert into RMS_CT_FEE_TYPE (ID, NO, NAME, FEE_DIR, INTR_BASE, INTR_BASE_CT, INTR_BASE_OTHER, INTR_TYPE, CREATE_TIME, LAST_UPDATE_TIME) values (3, '3', '教育费附加', '2', 1, 2, null, 1, to_date('22-10-2012 10:20:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-10-2012 10:20:19', 'dd-mm-yyyy hh24:mi:ss'))",
        "insert into RMS_CT_FEE_TYPE (ID, NO, NAME, FEE_DIR, INTR_BASE, INTR_BASE_CT, INTR_BASE_OTHER, INTR_TYPE, CREATE_TIME, LAST_UPDATE_TIME) values (4, '4', '地方教育费', '2', 1, 2, null, 1, to_date('22-10-2012 10:20:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-10-2012 10:20:34', 'dd-mm-yyyy hh24:mi:ss'))",
        "insert into RMS_CT_FEE_TYPE (ID, NO, NAME, FEE_DIR, INTR_BASE, INTR_BASE_CT, INTR_BASE_OTHER, INTR_TYPE, CREATE_TIME, LAST_UPDATE_TIME) values (5, '5', '委贷手续费', '2', 1, 0, null, 1, to_date('22-10-2012 10:21:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-10-2012 10:21:01', 'dd-mm-yyyy hh24:mi:ss'))",
        "insert into RMS_CT_FEE_TYPE (ID, NO, NAME, FEE_DIR, INTR_BASE, INTR_BASE_CT, INTR_BASE_OTHER, INTR_TYPE, CREATE_TIME, LAST_UPDATE_TIME) values (6, '6', '保函手续费', '2', 1, 0, null, 1, to_date('22-10-2012 10:21:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-10-2012 10:21:59', 'dd-mm-yyyy hh24:mi:ss'))",
        "insert into RMS_CT_FEE_TYPE (ID, NO, NAME, FEE_DIR, INTR_BASE, INTR_BASE_CT, INTR_BASE_OTHER, INTR_TYPE, CREATE_TIME, LAST_UPDATE_TIME) values (7, '7', '承兑手续费', '2', 1, 0, null, 1, to_date('22-10-2012 10:22:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-10-2012 10:22:16', 'dd-mm-yyyy hh24:mi:ss'))"};
        testEnvInst.getTarSqlExe().executeSQL(tmplSql);
    }
}
