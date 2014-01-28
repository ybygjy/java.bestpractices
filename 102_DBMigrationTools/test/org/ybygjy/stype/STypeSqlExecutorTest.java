package org.ybygjy.stype;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.ybygjy.IntegrationTests;
import org.ybygjy.TestModel;
import org.ybygjy.exec.SqlExecutorListener;
import org.ybygjy.meta.model.TableMeta;
import org.ybygjy.sql.model.SqlModel;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.SysConstants;

/**
 * @author WangYanCheng
 * @version 2012-9-26
 */
public class STypeSqlExecutorTest extends IntegrationTests {
    /** target Connection */
    public static final String DB_TARCONN_ORACLE = "jdbc:oracle:thin:NSTCSA3382/045869@192.168.0.143:1521/NSDEV";

    @Override
    protected String[] getTableName() {
        return new String[] { "EDC_MESSAGE" };
    }

    /*
     * (non-Javadoc)
     * @see org.ybygjy.IntegrationTests#getSrcConnection()
     */
    @Override
    protected Connection getSrcConnection() {
        return DBUtils.createConn4Oracle(SysConstants.DB_URL_ORACLE);
    }

    /*
     * (non-Javadoc)
     * @see org.ybygjy.IntegrationTests#getTargetConnection()
     */
    @Override
    protected Connection getTargetConnection() {
        return DBUtils.createConn4Oracle(DB_TARCONN_ORACLE);
    }

    /*
     * (non-Javadoc)
     * @see org.ybygjy.IntegrationTests#getTestModel()
     */
    @Override
    protected TestModel getTestModel() {
        return TestModel.ORA_ORA;
    }

    @Override
    public void doWork() {
        for (String tableName : getTableName()) {
            TableMeta publicTableMeta = super.getCommonTableMeta(tableName);
            final SqlModel oracleSqlModel = testEnvInst.getTarSqlMgr().buildInsertClobSQL(publicTableMeta);
            SqlModel mssqlSqlModel = testEnvInst.getSrcSqlMgr().buildQryClobSQL(publicTableMeta);
            testEnvInst.getSrcSTypeExe().addListener(new SqlExecutorListener() {
                @Override
                public void afterSpecialTypeQuery(SqlModel sqlModel, ResultSet rs) {
                    testEnvInst.getTarSTypeExe().executeSpecialTypeInsert(sqlModel, oracleSqlModel, rs);
                }
                
                @Override
                public void afterQuery(SqlModel smInst, List<Map<String, Object>> dataMap) {
                    return;
                }
            });
            testEnvInst.getSrcSTypeExe().executeQuerySpecialType(mssqlSqlModel);
        }
    }
}
