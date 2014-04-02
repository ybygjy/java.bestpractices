package org.ybygjy.dbcompare.task.oracle;

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
 * 对象状态非法明细
 * @author WangYanCheng
 * @version 2011-10-19
 */
public class InvalidObjCompare4Oracle extends AbstractTask {
	/**查询对象非法SQL*/
	private static String tmplIVOSql = "SELECT UPPER(OBJECT_NAME) AS OBJECT_NAME,OBJECT_TYPE,STATUS FROM ALL_OBJECTS WHERE OWNER='@SRC' AND STATUS <> 'VALID' ORDER BY OBJECT_TYPE, OBJECT_NAME";

	/**
	 * 对象状态非法明细
	 * @param srcUser 原始用户
	 * @param targetUser 参照用户
	 */
	public InvalidObjCompare4Oracle(String srcUser, String targetUser) {
		super(srcUser, targetUser);
		getCommonModel().getTaskInfo().setTaskName("对象状态非法明细");
		getCommonModel().getTaskInfo().setTaskType(MetaType.INVALID_OBJ);
	}

	@Override
	public void execute() {
		beforeListener();
		Statement stmt = null;
		try {
			stmt = DBUtils.createStmt(conn);
			getCommonModel().putRawData(MetaConstant.OBJ_INVALIDDETAIL, queryInvalidObjArr(stmt));
		} catch (SQLException e) {
			getCommonModel().putRawData(MetaConstant.OBJ_INVALIDDETAIL, null);
			e.printStackTrace();
		} finally {
			DBUtils.close(stmt);
		}
		getCommonModel().getTaskInfo().setFinished(true);
		afterListener();
	}
	
	/**
	 * 查询状态非法对象明细
	 * @param stmt SQL查询对象
	 * @return rtnMap rtnMap
	 * @throws SQLException 抛出异常做Log
	 */
	private Map<MetaType, List<AbstractObjectMeta>> queryInvalidObjArr(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery(tmplIVOSql.replaceFirst("@SRC", srcUser));
		List<AbstractObjectMeta> funcList = new ArrayList<AbstractObjectMeta>();
		List<AbstractObjectMeta> procList = new ArrayList<AbstractObjectMeta>();
		List<AbstractObjectMeta> trigList = new ArrayList<AbstractObjectMeta>();
		List<AbstractObjectMeta> viewList = new ArrayList<AbstractObjectMeta>();
		List<AbstractObjectMeta> packList = new ArrayList<AbstractObjectMeta>();
		List<AbstractObjectMeta> noneList = new ArrayList<AbstractObjectMeta>();
		while (rs.next()) {
			String objType = rs.getString("OBJECT_TYPE");
			String objName = rs.getString("OBJECT_NAME");
			AbstractObjectMeta dboInst = new AbstractObjectMeta(objName); 
			if ("PROCEDURE".equals(objType)) {
				dboInst.setObjectType(MetaType.PROC_OBJ);
				procList.add(dboInst);
			} else if ("PACKAGE".equals(objType)) {
				dboInst.setObjectType(MetaType.PACKAGE_OBJ);
				packList.add(dboInst);
			} else if ("VIEW".equals(objType)) {
				dboInst.setObjectType(MetaType.VIEW_OBJ);
				viewList.add(dboInst);
			} else if ("TRIGGER".equals(objType)) {
				dboInst.setObjectType(MetaType.TRIG_OBJ);
				trigList.add(dboInst);
			} else if ("FUNCTION".equals(objType)) {
				dboInst.setObjectType(MetaType.FUNC_OBJ);
				funcList.add(dboInst);
			} else {
				dboInst.setObjectType(MetaType.NONE_OBJ);
				noneList.add(dboInst);
			}
		}
		Map<MetaType, List<AbstractObjectMeta>> rtnMap = new HashMap<MetaType, List<AbstractObjectMeta>>();
		rtnMap.put(MetaType.FUNC_OBJ, funcList);
		rtnMap.put(MetaType.PROC_OBJ, procList);
		rtnMap.put(MetaType.VIEW_OBJ, viewList);
		rtnMap.put(MetaType.TRIG_OBJ, trigList);
		rtnMap.put(MetaType.PACKAGE_OBJ, packList);
		rtnMap.put(MetaType.NONE_OBJ, noneList);
		return rtnMap;
	}
}
