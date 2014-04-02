package org.ybygjy.dbcompare.task.oracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ybygjy.dbcompare.DBUtils;
import org.ybygjy.dbcompare.model.MetaConstant;
import org.ybygjy.dbcompare.model.MetaType;
import org.ybygjy.dbcompare.model.TypeMeta;
import org.ybygjy.dbcompare.task.AbstractTask;


/**
 * 类型对象结构比较Oracle实现
 * @author WangYanCheng
 * @version 2011-10-10
 */
public class TypeObjCompare4Oracle extends AbstractTask {
    /**SQL模版*/
    private static String tmplSQL = "SELECT '@T' TYPE, TYPE_NAME,TYPE_OID,TYPECODE,ATTRIBUTES FROM ALL_TYPES A WHERE OWNER='@SRC' AND NOT EXISTS(SELECT 1 FROM ALL_TYPES B WHERE OWNER='@TAR' AND B.TYPE_NAME = A.TYPE_NAME)";
    /**
     * 类型对象结构比较
     * @param srcUser 原用户
     * @param targetUser 参照用户
     */
    public TypeObjCompare4Oracle(String srcUser, String targetUser) {
        super(srcUser, targetUser);
        getCommonModel().getTaskInfo().setTaskName("类型对象结构比较");
        getCommonModel().getTaskInfo().setTaskType(MetaType.TYPE_OBJ);
    }

    @Override
    public void execute() {
        beforeListener();
        Statement stmt = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, queryTypeLostAndExcess(stmt));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(stmt);
        }
        getCommonModel().getTaskInfo().setFinished(true);
        afterListener();
    }
    /**
     * 查询类型缺失/多余明细
     * @param stmt SQL语句执行实例
     * @return rtnMap rtnMap
     * @throws SQLException SQLException
     */
    private Map<String, List<TypeMeta>> queryTypeLostAndExcess(Statement stmt) throws SQLException {
        String srcUser = getCommonModel().getTaskInfo().getSrcUser();
        String tarUser = getCommonModel().getTaskInfo().getTargetUser();
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(tmplSQL.replaceFirst("@T", "1").replaceFirst("@SRC", tarUser).replaceFirst("@TAR", srcUser))
            .append(" UNION ALL ")
            .append(tmplSQL.replaceFirst("@T", "1").replaceFirst("@TAR", tarUser).replaceFirst("@SRC", srcUser));
        ResultSet rs = stmt.executeQuery(sbuf.toString());
        List<TypeMeta> lostArr = new ArrayList<TypeMeta>();
        List<TypeMeta> excessArr = new ArrayList<TypeMeta>();
        while (rs.next()) {
            int type = rs.getInt("TYPE");
            TypeMeta typeMeta = new TypeMeta(rs.getString("TYPE_NAME"));
            typeMeta.setTypeOid(rs.getString("TYPE_OID"));
            typeMeta.setTypeCode(rs.getString("TYPECODE"));
            typeMeta.setAttCounts(rs.getInt("ATTRIBUTES"));
            if (MetaConstant.FLAG_LOST == type) {
                lostArr.add(typeMeta);
            } else {
                excessArr.add(typeMeta);
            }
        }
        Map<String, List<TypeMeta>> rtnMap = new HashMap<String, List<TypeMeta>>();
        rtnMap.put(MetaConstant.OBJ_LOST, lostArr);
        rtnMap.put(MetaConstant.OBJ_EXCESS, excessArr);
        return rtnMap;
    }
}
