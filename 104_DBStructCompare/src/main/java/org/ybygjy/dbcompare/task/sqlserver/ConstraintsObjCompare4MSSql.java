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
import org.ybygjy.dbcompare.model.ConstraintMeta;
import org.ybygjy.dbcompare.model.MetaConstant;
import org.ybygjy.dbcompare.model.MetaType;
import org.ybygjy.dbcompare.task.AbstractTask;


/**
 * 对象约束结构比较任务
 * @author WangYanCheng
 * @version 2011-10-17
 */
public class ConstraintsObjCompare4MSSql extends AbstractTask {
	private static String tmplLAFSql;
	static {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("SELECT * FROM (")
			.append("SELECT ")
			.append("(CASE WHEN A.A_TABLE_NAME IS NULL THEN B.B_TABLE_NAME ELSE A.A_TABLE_NAME END) TABLE_NAME,")
			.append("(CASE WHEN A.A_CONS_NAME IS NULL THEN B.B_CONS_NAME ELSE A.A_CONS_NAME END) CONS_NAME,")
			.append("(CASE WHEN A.A_CONS_NAME IS NULL THEN '1' WHEN B.B_CONS_NAME IS NULL THEN '2' ELSE '-1' END) TYPE ")
			.append("FROM (")
			.append("SELECT UPPER(TABLE_NAME) A_TABLE_NAME, UPPER(CONSTRAINT_NAME) A_CONS_NAME FROM @SRC.INFORMATION_SCHEMA.CONSTRAINT_TABLE_USAGE ) A FULL JOIN(")
			.append("SELECT UPPER(TABLE_NAME) B_TABLE_NAME, UPPER(CONSTRAINT_NAME) B_CONS_NAME FROM @TAR.INFORMATION_SCHEMA.CONSTRAINT_TABLE_USAGE ) B ")
			.append("ON A.A_TABLE_NAME = B.B_TABLE_NAME AND A.A_CONS_NAME = B.B_CONS_NAME ")
			.append(") C WHERE C.TYPE <> '-1'");
		tmplLAFSql = sbuf.toString();
	}
	/**
	 * 对象约束结构比较任务构造函数
	 * @param srcUser 原始用户
	 * @param targetUser 参照用户
	 */
	public ConstraintsObjCompare4MSSql(String srcUser, String targetUser) {
		super(srcUser, targetUser);
		getCommonModel().getTaskInfo().setTaskName("对象约束结构比较任务");
		getCommonModel().getTaskInfo().setTaskType(MetaType.CONS_OBJ);
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
	 * @param stmt SQL语句执行对象
	 * @return rtnMap rtnMap
	 * @throws SQLException 抛出异常做Log
	 */
	private Map<String, List<AbstractObjectMeta>> queryObjLostAndExcess(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery(tmplLAFSql.replaceFirst("@SRC", srcUser).replaceFirst("@TAR", targetUser));
		List<AbstractObjectMeta> lostArr = new ArrayList<AbstractObjectMeta>();
		List<AbstractObjectMeta> excessArr = new ArrayList<AbstractObjectMeta>();
		while (rs.next()) {
			AbstractObjectMeta dbomInst = new AbstractObjectMeta(rs.getString("TABLE_NAME"));
			ConstraintMeta dbcInst = new ConstraintMeta(rs.getString("CONS_NAME"));
			dbcInst.setDbomInst(dbomInst);
			dbomInst.addConstraint(dbcInst);
			if (MetaConstant.FLAG_LOST == rs.getInt("TYPE")) {
				lostArr.add(dbomInst);
			} else {
				excessArr.add(dbomInst);
			}
		}
		Map<String, List<AbstractObjectMeta>> rtnMap = new HashMap<String, List<AbstractObjectMeta>>();
		rtnMap.put(MetaConstant.OBJ_LOST, lostArr);
		rtnMap.put(MetaConstant.OBJ_EXCESS, excessArr);
		return rtnMap;
	}

}
