package org.ybygjy.exp;

import org.ybygjy.TestEnv;

/**
 * DBMSÄ£¿é´¦Àí
 * @author WangYanCheng
 * @version 2012-10-22
 */
public class ExpInterface4DBMS extends AbstractExpInterface {
    
    public ExpInterface4DBMS(TestEnv testEnvInst) {
        super(testEnvInst);
    }

    @Override
    public void beforeMigration() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void afterMigration() {
        testEnvInst.getTarSqlExe().executeSQL("UPDATE (SELECT ETB.BIZTP FROM EDC_TXBUSIDETAIL ETB JOIN EDC_TXDETAIL ETD ON ETB.TXDETAILID = ETD.TXDETAILID JOIN EDC_BILL EB ON ETD.BILLID = EB.BILLID WHERE IDNB IN (SELECT BILLNO FROM DBMS_BILL WHERE 1 = 1 AND BILLKIND = 1 AND ACTIVEFLAG = 1 AND STATUS = '126')) SET BIZTP = 'SP00'");
    }

}
