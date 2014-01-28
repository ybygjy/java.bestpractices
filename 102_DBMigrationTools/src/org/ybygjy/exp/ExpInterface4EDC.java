package org.ybygjy.exp;

import org.ybygjy.TestEnv;

/**
 * EDCÄ£¿éÏà¹Ø
 * @author WangYanCheng
 * @version 2012-10-24
 */
public class ExpInterface4EDC extends AbstractExpInterface {
    
    /**
     * Constructor
     * @param testEnvInst
     */
    public ExpInterface4EDC(TestEnv testEnvInst) {
        super(testEnvInst);
    }

    @Override
    public void beforeMigration() {
        testEnvInst.getTarSqlExe().executeSQL("ALTER TABLE EDC_MESSAGE MODIFY MSGTYPE CHAR(10)");
    }

    @Override
    public void afterMigration() {
    }

}
