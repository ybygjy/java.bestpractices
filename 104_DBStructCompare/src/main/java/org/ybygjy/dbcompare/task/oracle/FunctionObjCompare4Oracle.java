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
 * 函数对象结构比较Oracle实现
 * @author WangYanCheng
 * @version 2011-10-10
 */
public class FunctionObjCompare4Oracle extends AbstractTask {
    /**SQL模版*/
    private static String tmplSQL = "SELECT '@T' TYPE, OBJECT_NAME FROM ALL_PROCEDURES A WHERE OWNER = '@SRC' AND OBJECT_TYPE = 'FUNCTION' AND NOT EXISTS (SELECT 1 FROM ALL_PROCEDURES B WHERE B.OWNER = '@TAR' AND OBJECT_TYPE = 'FUNCTION' AND B.OBJECT_NAME = A.OBJECT_NAME)";
    /**
     * 函数对象实例构造函数
     * @param srcUser 原始用户
     * @param targetUser 参照用户
     */
    public FunctionObjCompare4Oracle(String srcUser, String targetUser) {
        super(srcUser, targetUser);
        getCommonModel().getTaskInfo().setTaskName("函数对象结构比较");
        getCommonModel().getTaskInfo().setTaskType(MetaType.FUNC_OBJ);
    }

    @Override
    public void execute() {
        beforeListener();
        Statement stmt = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            Map<String, List<AbstractObjectMeta>> lostAndExcessColl = queryFunctionLostAndExcess(stmt);
            getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, lostAndExcessColl);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(stmt);
        }
        getCommonModel().getTaskInfo().setFinished(true);
        afterListener();
    }
    /**
     * 取函数缺失/多余明细
     * @param stmt SQL语句执行实例
     * @return rtnMap rtnMap
     * @throws SQLException 抛出异常做Log
     */
    private Map<String, List<AbstractObjectMeta>> queryFunctionLostAndExcess(Statement stmt) throws SQLException {
        String srcUser = getCommonModel().getTaskInfo().getSrcUser();
        String tarUser = getCommonModel().getTaskInfo().getTargetUser();
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(tmplSQL.replaceFirst("@T", "2").replaceFirst("@SRC", srcUser).replaceFirst("@TAR", tarUser))
            .append(" UNION ALL ")
            .append(tmplSQL.replaceFirst("@T", "1").replaceFirst("@SRC", tarUser).replaceFirst("@TAR", srcUser));
        ResultSet rs = stmt.executeQuery(sbuf.toString());
        List<AbstractObjectMeta> lostArr = new ArrayList<AbstractObjectMeta>();
        List<AbstractObjectMeta> excessArr = new ArrayList<AbstractObjectMeta>();
        while (rs.next()) {
            int type = rs.getInt("TYPE");
            String objectName = rs.getString("OBJECT_NAME");
            AbstractObjectMeta tmpTOInst = new AbstractObjectMeta(objectName, MetaType.FUNC_OBJ);
            if (MetaConstant.FLAG_LOST == type) {
                lostArr.add(tmpTOInst);
            } else {
                excessArr.add(tmpTOInst);
            }
        }
        Map<String, List<AbstractObjectMeta>> rtnMap = new HashMap<String, List<AbstractObjectMeta>>();
        rtnMap.put(MetaConstant.OBJ_LOST, lostArr);
        rtnMap.put(MetaConstant.OBJ_EXCESS, excessArr);
        return rtnMap;
    }
}
