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
 * 表对象结构比较任务
 * @author WangYanCheng
 * @version 2011-10-13
 */
public class TableObjCompare4MSSql extends AbstractTask {
	/**统计缺失/多余明细SQL模版*/
	private static String tmplLAESql = "SELECT '@T' TYPE, A.OBJECT_ID,UPPER(A.NAME) AS TABLE_NAME FROM @SRC.SYS.TABLES A WHERE NOT EXISTS(SELECT 1 FROM @TAR.SYS.TABLES B WHERE UPPER(B.NAME) = UPPER(A.NAME))";
	/**统计表对象数量*/
	private static String tmplCountSql = "SELECT * FROM (SELECT COUNT(1) @SRC FROM @SRC.SYS.TABLES)A, (SELECT COUNT(1) @TAR FROM @TAR.SYS.TABLES) B;";
	/**
	 * 表对象结构比较任务构造方法
	 * @param srcUser 原始用户
	 * @param targetUser 参照用户
	 */
	public TableObjCompare4MSSql(String srcUser, String targetUser) {
		super(srcUser, targetUser);
		getCommonModel().getTaskInfo().setTaskName("表对象结构比较任务");
		getCommonModel().getTaskInfo().setTaskType(MetaType.TABLE_OBJ);
	}

	@Override
	public void execute() {
		beforeListener();
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			getCommonModel().putRawData(MetaConstant.OBJ_COUNT, queryObjectCount(stmt));
			getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, queryObjLostAndExcess(stmt));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(stmt);
		}
		getCommonModel().getTaskInfo().setFinished(true);
		beforeListener();
	}
	/**
	 * 查询对象缺失/多余明细
	 * @param stmt SQL语句执行器
	 * @return rtnMap rtnMap
	 * @throws SQLException 抛出异常做Log
	 */
	private Map<String, List<AbstractObjectMeta>> queryObjLostAndExcess(Statement stmt) throws SQLException {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(tmplLAESql.replaceFirst("@T", "2").replaceFirst("@SRC", srcUser).replaceFirst("@TAR", targetUser))
			.append(" UNION ALL ")
			.append(tmplLAESql.replaceFirst("@T", "1").replaceFirst("@SRC", targetUser).replaceFirst("@TAR", srcUser));
		ResultSet rs = stmt.executeQuery(sbuf.toString());
		List<AbstractObjectMeta> lostList = new ArrayList<AbstractObjectMeta>();
		List<AbstractObjectMeta> excessList = new ArrayList<AbstractObjectMeta>();
		while (rs.next()) {
			int type = rs.getInt("TYPE");
			AbstractObjectMeta omoInst = new AbstractObjectMeta();
			omoInst.setObjectType(MetaType.TABLE_OBJ);
			omoInst.setObjectName(rs.getString("TABLE_NAME"));
			if (MetaConstant.FLAG_LOST == type) {
				lostList.add(omoInst);
			} else {
				excessList.add(omoInst);
			}
		}
		Map<String, List<AbstractObjectMeta>> rtnMap = new HashMap<String, List<AbstractObjectMeta>>();
		rtnMap.put(MetaConstant.OBJ_LOST, lostList);
		rtnMap.put(MetaConstant.OBJ_EXCESS, excessList);
		return rtnMap;
	}
	/**
	 * 查询对象数量
	 * @param stmt SQL语句执行器
	 * @return rtnMap rtnMap
	 * @throws SQLException 抛出异常做Log
	 */
	private Map<String, String> queryObjectCount(Statement stmt) throws SQLException {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(tmplCountSql.replaceAll("@SRC", srcUser).replaceAll("@TAR", targetUser));
		ResultSet rs = stmt.executeQuery(sbuf.toString());
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put(srcUser, "0");
		rtnMap.put(targetUser, "0");
		if (rs.next()) {
			rtnMap.put(srcUser, rs.getString(srcUser));
			rtnMap.put(targetUser, rs.getString(targetUser));
		}
		return rtnMap;
	}
}
