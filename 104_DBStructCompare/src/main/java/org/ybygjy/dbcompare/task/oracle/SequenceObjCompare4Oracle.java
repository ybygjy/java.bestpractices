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
 * 序列对象比较
 * @author WangYanCheng
 * @version 2011-10-9
 */
public class SequenceObjCompare4Oracle extends AbstractTask {
    
    /**模版SQL*/
    private static String sqlTmpl= "SELECT '@F' TYPE, SEQUENCE_OWNER,SEQUENCE_NAME FROM ALL_SEQUENCES A WHERE SEQUENCE_OWNER='@SRC' AND NOT EXISTS(SELECT 1 FROM ALL_SEQUENCES B WHERE B.SEQUENCE_OWNER='@TAR' AND B.SEQUENCE_NAME = A.SEQUENCE_NAME) ";
    /**
     * 序列对象比较实例构造器
     * @param srcUser 原始用户
     * @param targetUser 参照用户
     */
    public SequenceObjCompare4Oracle(String srcUser, String targetUser) {
        super(srcUser, targetUser);
        getCommonModel().getTaskInfo().setTaskName("序列对象结构比较");
        getCommonModel().getTaskInfo().setTaskType(MetaType.SEQ_OBJ);
    }

    @Override
    public void execute() {
        beforeListener();
        Statement stmt = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            // 统计序列缺失/多余信息
            Map<String, List<AbstractObjectMeta>> lostAndExcessArr = queryLostAndExcess(stmt);
            getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, lostAndExcessArr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(stmt);
        }
        getCommonModel().getTaskInfo().setFinished(true);
        afterListener();
    }

    private Map<String,List<AbstractObjectMeta>> queryLostAndExcess(Statement stmt) throws SQLException {
        String srcUser = getCommonModel().getTaskInfo().getSrcUser();
        String tarUser = getCommonModel().getTaskInfo().getTargetUser();
        StringBuffer exeSql = new StringBuffer();
        exeSql.append(sqlTmpl.replaceFirst("@F", "1").replaceFirst("@SRC", tarUser).replaceFirst("@TAR", srcUser))
            .append(" UNION ALL ")
            .append(sqlTmpl.replaceFirst("@F", "2").replaceFirst("@SRC", srcUser).replaceFirst("@TAR", tarUser));
        ResultSet rs = stmt.executeQuery(exeSql.toString());
        List<AbstractObjectMeta> lostList = new ArrayList<AbstractObjectMeta>();
        List<AbstractObjectMeta> excessList = new ArrayList<AbstractObjectMeta>();
        while (rs.next()) {
            int type = rs.getInt("TYPE");
            AbstractObjectMeta goInst = new AbstractObjectMeta(rs.getString("SEQUENCE_NAME"));
            goInst.setOwner(rs.getString("SEQUENCE_OWNER"));
            if (type == MetaConstant.FLAG_LOST) {
                lostList.add(goInst);
            } else {
                excessList.add(goInst);
            }
        }
        Map<String, List<AbstractObjectMeta>> rtnMap = new HashMap<String, List<AbstractObjectMeta>>();
        rtnMap.put(MetaConstant.OBJ_LOST, lostList);
        rtnMap.put(MetaConstant.OBJ_EXCESS, excessList);
        return rtnMap;
    }
}
