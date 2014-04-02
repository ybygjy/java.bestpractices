package org.ybygjy.dbcompare.task.oracle;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.ybygjy.dbcompare.DBUtils;
import org.ybygjy.dbcompare.model.AbstractObjectMeta;
import org.ybygjy.dbcompare.model.MetaConstant;
import org.ybygjy.dbcompare.model.MetaType;
import org.ybygjy.dbcompare.model.TriggerMeta;
import org.ybygjy.dbcompare.task.AbstractTask;


/**
 * 触发器对象结构比较
 * @author WangYanCheng
 * @version 2011-10-9
 */
public class TriggerObjCompare4Oracle extends AbstractTask {
    /**SQL模版*/
    private static String tmplSql = "SELECT '@T' TYPE,A.TABLE_NAME,A.TRIGGER_NAME,A.TRIGGER_TYPE,A.TRIGGERING_EVENT,A.BASE_OBJECT_TYPE,A.WHEN_CLAUSE,A.STATUS FROM ALL_TRIGGERS A WHERE OWNER='@SRC' AND NOT EXISTS(SELECT 1 FROM ALL_TRIGGERS B WHERE B.OWNER='@TAR' AND B.TABLE_NAME = A.TABLE_NAME AND B.TRIGGER_NAME=A.TRIGGER_NAME)";
    /**
     * 触发器对象结构比较构造器
     * @param srcUser 原始用户
     * @param targetUser 参照用户
     */
    public TriggerObjCompare4Oracle(String srcUser, String targetUser) {
        super(srcUser, targetUser);
        getCommonModel().getTaskInfo().setTaskName("触发器对象结构比较");
        getCommonModel().getTaskInfo().setTaskType(MetaType.TRIG_OBJ);
    }

    @Override
    public void execute() {
        beforeListener();
        Statement stmt = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            Map<String, Map<String, AbstractObjectMeta>> lostAndExcessMap = queryTriggLostAndExcess(stmt);
            Map<String, AbstractObjectMeta> triggCompDetail = queryTriggStructCmp(stmt);
            getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, lostAndExcessMap);
            getCommonModel().putRawData(MetaConstant.OBJ_COMPAREDETAIL, triggCompDetail);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(stmt);
        }
        getCommonModel().getTaskInfo().setFinished(true);
        afterListener();
    }

    /**
     * 取触发器对象内容比较
     * @param stmt SQL语句执行实例
     * @return rtnMap rtnMap
     * @throws SQLException 抛出异常做Log
     */
    private Map<String, AbstractObjectMeta> queryTriggStructCmp(Statement stmt) throws SQLException {
        callProcedureStore();
        String tmplSql = "SELECT * FROM SYSTEM.NSTC_DBCOMP_TRIGG";
        ResultSet rs = stmt.executeQuery(tmplSql);
        Map<String, AbstractObjectMeta> rtnMap = new HashMap<String, AbstractObjectMeta>();
        while(rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            AbstractObjectMeta tableObj = null;
            if (rtnMap.containsKey(tableName)) {
                tableObj = rtnMap.get(tableName);
            } else {
                tableObj = new AbstractObjectMeta(tableName);
                rtnMap.put(tableName, tableObj);
            }
            TriggerMeta triggObj = new TriggerMeta();
            triggObj.setTableInst(tableObj);
            triggObj.setTriggName(rs.getString("TRIGGER_NAME"));
            triggObj.setTriggType(rs.getString("TRIGGER_TYPE"));
            triggObj.setTriggEvent(rs.getString("TRIGGER_EVENT"));
            triggObj.setTriggObjType(rs.getString("TRIGGER_BOT"));
            triggObj.setTriggWhenClause(rs.getString("TRIGGER_WCLAUSE"));
            triggObj.setTriggStatus(rs.getString("TRIGGER_STATUS"));
            tableObj.addTrigger(triggObj);
        }
        return rtnMap;
    }
    /**
     * 调用存储过程
     * @throws SQLException 抛出异常做Log
     */
    private void callProcedureStore() throws SQLException {
        String sql = "{call SYSTEM.NSTC_DBCOMPARE4TRIGG(?,?)}";
        CallableStatement callStmt = conn.prepareCall(sql);
        callStmt.setString(1, getCommonModel().getTaskInfo().getSrcUser());
        callStmt.setString(2, getCommonModel().getTaskInfo().getTargetUser());
        callStmt.execute();
    }
    /**
     * 取触发器对象结构缺失/多余明细
     * @param stmt SQL语句执行实例
     * @return rtnMap rtnMap
     * @throws SQLException 抛出异常做Log
     */
    private Map<String, Map<String, AbstractObjectMeta>> queryTriggLostAndExcess(Statement stmt) throws SQLException {
        String srcUser = contextModel.getTaskInfo().getSrcUser();
        String tarUser = contextModel.getTaskInfo().getTargetUser();
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(tmplSql.replaceFirst("@T", "2").replaceFirst("@SRC", srcUser).replaceFirst("@TAR", tarUser))
            .append(" UNION ALL ")
            .append(tmplSql.replaceFirst("@T", "1").replaceFirst("@SRC", tarUser).replaceFirst("@TAR", srcUser));
        ResultSet rs = stmt.executeQuery(sbuf.toString());
        Map<String, AbstractObjectMeta> lostColl = new HashMap<String, AbstractObjectMeta>();
        Map<String, AbstractObjectMeta> excessColl = new HashMap<String, AbstractObjectMeta>();
        while (rs.next()) {
            int type = rs.getInt("TYPE");
            String tableName = rs.getString("TABLE_NAME");
            String tableType = rs.getString("BASE_OBJECT_TYPE");
            TriggerMeta triggObj = new TriggerMeta();
            triggObj.setTriggName(rs.getString("TRIGGER_NAME"));
            triggObj.setTriggType(rs.getString("TRIGGER_TYPE"));
            triggObj.setTriggEvent(rs.getString("TRIGGERING_EVENT"));
            triggObj.setTriggObjType(tableType);
            triggObj.setTriggWhenClause(rs.getString("WHEN_CLAUSE"));
            triggObj.setTriggStatus(rs.getString("STATUS"));
            AbstractObjectMeta tableObj = null;
            if (MetaConstant.FLAG_LOST == type) {
                if (lostColl.containsKey(tableName)) {
                    tableObj = lostColl.get(tableName);
                } else {
                    tableObj = new AbstractObjectMeta(tableName, getTaskType(tableType));
                    lostColl.put(tableName, tableObj);
                }
            } else {
                if (excessColl.containsKey(tableName)) {
                    tableObj = excessColl.get(tableName);
                } else {
                    tableObj = new AbstractObjectMeta(tableName, getTaskType(tableType));
                    excessColl.put(tableName, tableObj);
                }
            }
            triggObj.setTableInst(tableObj);
            tableObj.addTrigger(triggObj);
        }
        Map<String, Map<String, AbstractObjectMeta>> rtnMap = new HashMap<String, Map<String,AbstractObjectMeta>>();
        rtnMap.put(MetaConstant.OBJ_LOST, lostColl);
        rtnMap.put(MetaConstant.OBJ_EXCESS, excessColl);
        return rtnMap;
    }
    private MetaType getTaskType(String type) {
        return "VIEW".equals(type) ? MetaType.VIEW_OBJ : MetaType.TABLE_OBJ;
    }
}
