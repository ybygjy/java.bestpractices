package org.ybygjy.dbcompare.task.sqlserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.ybygjy.dbcompare.DBUtils;
import org.ybygjy.dbcompare.model.AbstractObjectFieldMeta;
import org.ybygjy.dbcompare.model.AbstractObjectMeta;
import org.ybygjy.dbcompare.model.MetaConstant;
import org.ybygjy.dbcompare.model.MetaType;
import org.ybygjy.dbcompare.task.AbstractTask;


/**
 * 表字段对象结构比较任务
 * @author WangYanCheng
 * @version 2011-10-13
 */
public class TableFieldObjCompare4MSSql extends AbstractTask {
	/**字段缺失\多余*/
	private static String tmplLAESql = "SELECT '@T' TYPE, UPPER(A.TABLE_NAME) AS TABLE_NAME,UPPER(A.COLUMN_NAME) AS COLUMN_NAME FROM @SRC.INFORMATION_SCHEMA.COLUMNS A WHERE NOT EXISTS(SELECT 1 FROM @TAR.INFORMATION_SCHEMA.COLUMNS B WHERE B.TABLE_NAME = A.TABLE_NAME AND B.COLUMN_NAME = A.COLUMN_NAME)";
	private static String tmplDETAILSql;
	static {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("SELECT * FROM (")
			.append("SELECT A.TABLE_NAME,A.COLUMN_NAME,")
			.append("(CASE WHEN A.A_DATATYPE <> A.B_DATATYPE THEN (UPPER(A.A_DATATYPE) + '!='+ UPPER(A.B_DATATYPE)) ELSE NULL END) FIELD_TYPE,")
			/* 去掉字段顺序验证
			.append("(CASE WHEN A.A_ORD_POSI <> A.B_ORD_POSI THEN (UPPER(A.A_ORD_POSI) + '!='+ UPPER(A.B_ORD_POSI)) ELSE NULL END) FIELD_ORID,")
			*/
			.append("(CASE WHEN A.A_COL_DEF <> A.B_COL_DEF THEN (UPPER(A.A_COL_DEF) + '!=' + UPPER(A.B_COL_DEF)) ELSE NULL END) FIELD_DEFVALUE,")
			.append("(CASE WHEN A.A_ISNULL <> A.B_ISNULL THEN (UPPER(A.A_ISNULL) + '!=' + UPPER(A.B_ISNULL)) ELSE NULL END ) FIELD_ISNULL,")
			.append("(CASE WHEN A.A_CMAXLEN <> A.B_CMAXLEN THEN (UPPER(A.A_CMAXLEN) + '!=' + UPPER(A.B_CMAXLEN)) ELSE NULL END) FIELD_LEN,")
			.append("(CASE WHEN A.A_NUM_PREC <> A.B_NUM_PREC THEN (UPPER(A.A_NUM_PREC) + '!=' + UPPER(A.B_NUM_PREC)) ELSE NULL END) FIELD_PRE,")
			.append("(CASE WHEN A.A_NUMSCAL <> A.B_NUMSCAL THEN (UPPER(A.A_NUMSCAL) + '!=' + UPPER(A.B_NUMSCAL)) ELSE NULL END) FIELD_SCA")
			.append(" FROM (")
			.append("SELECT  UPPER(A.TABLE_NAME) AS TABLE_NAME,")
			.append("UPPER(A.COLUMN_NAME) AS COLUMN_NAME,A.ORDINAL_POSITION AS A_ORD_POSI,A.COLUMN_DEFAULT AS A_COL_DEF,")
			.append("UPPER(A.DATA_TYPE) A_DATATYPE,A.IS_NULLABLE AS A_ISNULL,A.CHARACTER_MAXIMUM_LENGTH AS A_CMAXLEN,")
			.append("A.NUMERIC_PRECISION AS A_NUM_PREC,A.NUMERIC_SCALE AS A_NUMSCAL,B.ORDINAL_POSITION AS B_ORD_POSI,")
			.append("B.COLUMN_DEFAULT AS B_COL_DEF,UPPER(B.DATA_TYPE) B_DATATYPE,B.IS_NULLABLE AS B_ISNULL,")
			.append("B.CHARACTER_MAXIMUM_LENGTH AS B_CMAXLEN,B.NUMERIC_PRECISION AS B_NUM_PREC,B.NUMERIC_SCALE AS B_NUMSCAL ")
			.append("FROM @SRC.INFORMATION_SCHEMA.COLUMNS A,@TAR.INFORMATION_SCHEMA.COLUMNS B ")
			.append("WHERE UPPER(A.TABLE_NAME) = UPPER(B.TABLE_NAME) AND UPPER(A.COLUMN_NAME) = UPPER(B.COLUMN_NAME)")
			.append(") A) B")
			.append(" WHERE B.FIELD_TYPE IS NOT NULL OR B.FIELD_DEFVALUE IS NOT NULL OR B.FIELD_ISNULL IS NOT NULL OR B.FIELD_LEN IS NOT NULL OR B.FIELD_PRE IS NOT NULL OR B.FIELD_SCA IS NOT NULL");
		tmplDETAILSql = sbuf.toString();
	}
	/**
	 * 表字段对象结构比较任务
	 * @param srcUser 原始用户
	 * @param targetUser 参照用户
	 */
	public TableFieldObjCompare4MSSql(String srcUser, String targetUser) {
		super(srcUser, targetUser);
		getCommonModel().getTaskInfo().setTaskName("表字段对象结构比较任务");
		getCommonModel().getTaskInfo().setTaskType(MetaType.TABLE_FIELDOBJ);
	}

	@Override
	public void execute() {
		beforeListener();
		Statement stmt = null;
		try {
			stmt = DBUtils.createStmt(conn);
			getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS,queryObjLostAndExcess(stmt));
			getCommonModel().putRawData(MetaConstant.OBJ_COMPAREDETAIL, queryObjCompareDetail(stmt));
		} catch (SQLException e) {
			getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, null);
			getCommonModel().putRawData(MetaConstant.OBJ_COMPAREDETAIL, null);
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
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(tmplLAESql.replaceFirst("@T", "1").replaceAll("@SRC", targetUser).replaceAll("@TAR", srcUser))
			.append(" UNION ALL ")
			.append(tmplLAESql.replaceFirst("@T", "2").replaceAll("@SRC", srcUser).replaceAll("@TAR", targetUser));
		ResultSet rs = stmt.executeQuery(sbuf.toString());
		Map<String, AbstractObjectMeta> lostMap = new HashMap<String, AbstractObjectMeta>();
		Map<String, AbstractObjectMeta> excessMap = new HashMap<String, AbstractObjectMeta>();
		while (rs.next()) {
			int type = rs.getInt("TYPE");
			String tableName = rs.getString("TABLE_NAME");
			AbstractObjectFieldMeta ofoInst = new AbstractObjectFieldMeta();
			ofoInst.setFieldCode(rs.getString("COLUMN_NAME"));
			AbstractObjectMeta omoInst = null;
			if (MetaConstant.FLAG_LOST == type) {
				omoInst = lostMap.containsKey(tableName) ? lostMap.get(tableName) : new AbstractObjectMeta(tableName);
				if (!lostMap.containsKey(tableName)) {
					lostMap.put(tableName, omoInst);
				} 
			} else {
				omoInst = excessMap.containsKey(tableName) ? excessMap.get(tableName) : new AbstractObjectMeta(tableName);
				if (!excessMap.containsKey(tableName)) {
					excessMap.put(tableName, omoInst);
				}
			}
			ofoInst.setTableObj(omoInst);
			omoInst.addField(ofoInst);
		}
		Map<String, Map<String, AbstractObjectMeta>> rtnMap = new HashMap<String, Map<String,AbstractObjectMeta>>();
		rtnMap.put(MetaConstant.OBJ_LOST, lostMap);
		rtnMap.put(MetaConstant.OBJ_EXCESS, excessMap);
		return rtnMap;
	}
	/**
	 * 查询字段对象内容比较明细
	 * @param stmt SQL语句执行实例
	 * @return rtnMap rtnMap
	 * @throws SQLException 抛出异常做Log
	 */
	private Map<String, AbstractObjectMeta> queryObjCompareDetail(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery(tmplDETAILSql.replaceFirst("@SRC", srcUser).replaceFirst("@TAR", targetUser));
		Map<String, AbstractObjectMeta> rtnMap = new HashMap<String, AbstractObjectMeta>();
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			AbstractObjectMeta omoInst = rtnMap.containsKey(tableName) ? rtnMap.get(tableName) : new AbstractObjectMeta(tableName);
			AbstractObjectFieldMeta ofoInst = new AbstractObjectFieldMeta(omoInst);
			ofoInst.setFieldCode(rs.getString("COLUMN_NAME"));
			ofoInst.setFieldType(rs.getString("FIELD_TYPE"));
			/*去掉字段顺序验证*/
			//ofoInst.setFieldOrder(rs.getString("FIELD_ORID"));
			ofoInst.setFieldDefValue(rs.getString("FIELD_DEFVALUE"));
			ofoInst.setFieldNull(rs.getString("FIELD_ISNULL"));
			ofoInst.setFieldLen(rs.getString("FIELD_LEN"));
			ofoInst.setFieldPre(rs.getString("FIELD_PRE"));
			ofoInst.setFieldSca(rs.getString("FIELD_SCA"));
			omoInst.addField(ofoInst);
			if (!rtnMap.containsKey(tableName)) {
				rtnMap.put(tableName, omoInst);
			}
		}
		return rtnMap;
	}
}
