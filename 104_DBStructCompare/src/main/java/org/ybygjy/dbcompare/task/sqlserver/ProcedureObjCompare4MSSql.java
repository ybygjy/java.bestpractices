package org.ybygjy.dbcompare.task.sqlserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ybygjy.dbcompare.DBUtils;
import org.ybygjy.dbcompare.model.AbstractObjectMeta;
import org.ybygjy.dbcompare.model.MetaConstant;
import org.ybygjy.dbcompare.model.MetaType;
import org.ybygjy.dbcompare.task.AbstractTask;


/**
 * 存储过程对象结构比较任务
 * @author WangYanCheng
 * @version 2011-10-14
 */
public class ProcedureObjCompare4MSSql extends AbstractTask {
	/**对象缺失/多余查询SQL*/
	private static String tmplLAESql = "SELECT C.TYPE,C.PRO_NAME FROM (SELECT (CASE WHEN A.NAME IS NULL THEN '1' WHEN B.NAME IS NULL THEN '2' ELSE '-1' END) TYPE,(CASE WHEN A.NAME IS NULL THEN UPPER(B.NAME) WHEN B.NAME IS NULL THEN UPPER(A.NAME) ELSE UPPER(A.NAME) END) PRO_NAME FROM @SRC.SYS.PROCEDURES A FULL JOIN (SELECT UPPER(NAME) NAME FROM @TAR.SYS.PROCEDURES ) B ON A.NAME = B.NAME) C WHERE C.TYPE <> '-1'";
	/**
	 * 存储过程对象结构比较任务构造函数
	 * @param srcUser 原始用户
	 * @param targetUser 参照用户
	 */
	public ProcedureObjCompare4MSSql(String srcUser, String targetUser) {
		super(srcUser, targetUser);
		getCommonModel().getTaskInfo().setTaskName("存储过程对象结构比较任务");
		getCommonModel().getTaskInfo().setTaskType(MetaType.PROC_OBJ);
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
	private Map<String, List<AbstractObjectMeta>> queryObjLostAndExcess(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery(tmplLAESql.replaceAll("@SRC", srcUser).replaceFirst("@TAR", targetUser));
		List<AbstractObjectMeta> lostList = new ArrayList<AbstractObjectMeta>();
		List<AbstractObjectMeta> excessList = new ArrayList<AbstractObjectMeta>();
		while (rs.next()) {
			int type = rs.getInt("TYPE");
			String objName = rs.getString("PRO_NAME");
			AbstractObjectMeta tmpObj = new AbstractObjectMeta(objName);
			tmpObj.setObjectType(MetaType.PROC_OBJ);
			boolean flag = (MetaConstant.FLAG_LOST == type) ? lostList.add(tmpObj) : excessList.add(tmpObj);
			if (!flag) {
				System.out.println("内部逻辑错误，请联系管理员！");
			}
		}
		Map<String, List<AbstractObjectMeta>> rtnMap = new HashMap<String, List<AbstractObjectMeta>>();
		rtnMap.put(MetaConstant.OBJ_LOST, lostList);
		rtnMap.put(MetaConstant.OBJ_EXCESS, excessList);
		return rtnMap;
	}

}
