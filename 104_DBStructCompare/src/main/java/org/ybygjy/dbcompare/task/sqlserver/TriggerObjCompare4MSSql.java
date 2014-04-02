package org.ybygjy.dbcompare.task.sqlserver;

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
 * 触发器对象结构比较任务
 * @author WangYanCheng
 * @version 2011-10-14
 */
public class TriggerObjCompare4MSSql extends AbstractTask {
	/**对象缺失/多余SQL*/
	private static String tmplLAESql;
	static {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("SELECT TABLE_NAME,TRIGGER_NAME, TYPE FROM (")
			.append("SELECT ")
			.append("(CASE WHEN A.TABLE_NAME IS NULL THEN B.TABLE_NAME WHEN B.TABLE_NAME IS NULL THEN A.TABLE_NAME ELSE A.TABLE_NAME END) TABLE_NAME,")
			.append("(CASE WHEN A.TRIGGER_NAME IS NULL THEN B.TRIGGER_NAME WHEN B.TRIGGER_NAME IS NULL THEN A.TRIGGER_NAME ELSE A.TRIGGER_NAME END) TRIGGER_NAME,")
			.append("(CASE WHEN A.TABLE_NAME IS NULL THEN '1' WHEN B.TABLE_NAME IS NULL THEN '2' ELSE '-1' END) TYPE ")
			.append("FROM (")
			.append("SELECT UPPER(B.NAME) TABLE_NAME, UPPER(A.NAME) TRIGGER_NAME FROM @SRC.SYS.TRIGGERS A LEFT JOIN @SRC.SYS.TABLES B ON A.PARENT_ID = B.OBJECT_ID")
		    .append(") A FULL JOIN (") 
		    .append("SELECT UPPER(B.NAME) TABLE_NAME, UPPER(A.NAME) TRIGGER_NAME FROM @TAR.SYS.TRIGGERS A LEFT JOIN @TAR.SYS.TABLES B ON A.PARENT_ID = B.OBJECT_ID")
			.append(") B ON A.TABLE_NAME = B.TABLE_NAME AND A.TRIGGER_NAME = B.TRIGGER_NAME").append(") C WHERE C.TYPE <> '-1'");
		tmplLAESql = sbuf.toString();
	}
	/**
	 * 触发器对象结构比较任务构造器
	 * @param srcUser 原始用户
	 * @param targetUser 参照用户
	 */
	public TriggerObjCompare4MSSql(String srcUser, String targetUser) {
		super(srcUser, targetUser);
		getCommonModel().getTaskInfo().setTaskName("触发器对象结构比较任务");
		getCommonModel().getTaskInfo().setTaskType(MetaType.TRIG_OBJ);
	}

	@Override
	public void execute() {
		beforeListener();
		Statement stmt = null;
		try {
			stmt = DBUtils.createStmt(conn);
			getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, queryObjLostAndExcess(stmt));
		} catch (SQLException e) {
			getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, null);
			e.printStackTrace();
		} finally {
			DBUtils.close(stmt);
		}
		getCommonModel().getTaskInfo().setFinished(true);
		afterListener();
	}
	/**
	 * 查询对象缺失/多余明细
	 * @param stmt SQL语句执行实例
	 * @return rtnMap rtnMap
	 * @throws SQLException 抛出异常做Log
	 */
	private Map<String, Map<String, AbstractObjectMeta>> queryObjLostAndExcess(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery(tmplLAESql.replaceAll("@SRC", srcUser).replaceAll("@TAR", targetUser));
		Map<String, AbstractObjectMeta> lostMap = new HashMap<String, AbstractObjectMeta>();
		Map<String, AbstractObjectMeta> excessMap = new HashMap<String, AbstractObjectMeta>();
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			if (null == tableName) {
				System.out.println("表名称为空==>" + tmplLAESql);
			}
			TriggerMeta toInst = new TriggerMeta();
			toInst.setTriggName(rs.getString("TRIGGER_NAME"));
			AbstractObjectMeta omInst = null;
			if (MetaConstant.FLAG_LOST == rs.getInt("TYPE")) {
				if (lostMap.containsKey(tableName)) {
					omInst = lostMap.get(tableName);
				} else {
					omInst = new AbstractObjectMeta(tableName, MetaType.TRIG_OBJ);
					lostMap.put(tableName, omInst);
				}
			} else {
				if (excessMap.containsKey(tableName)) {
					omInst = excessMap.get(tableName);
				} else {
					omInst = new AbstractObjectMeta(tableName, MetaType.TRIG_OBJ);
					excessMap.put(tableName, omInst);
				}
			}
			toInst.setTableInst(omInst);
			omInst.addTrigger(toInst);
		}
		Map<String, Map<String, AbstractObjectMeta>> rtnMap = new HashMap<String, Map<String,AbstractObjectMeta>>();
		rtnMap.put(MetaConstant.OBJ_LOST, lostMap);
		rtnMap.put(MetaConstant.OBJ_EXCESS, excessMap);
		return rtnMap;
	}

}
