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
 * 视图对象结构比较
 * @author WangYanCheng
 * @version 2011-10-10
 */
public class ViewObjCompare4Oracle extends AbstractTask {
    /**SQL模版*/
    private static String sqlTmpl = "SELECT '@T' TYPE, VIEW_NAME FROM ALL_VIEWS A WHERE OWNER = '@SRC' AND NOT EXISTS (SELECT 1 FROM ALL_VIEWS B WHERE OWNER = '@TAR' AND B.VIEW_NAME = A.VIEW_NAME)";
    /**
     * 构造方法
     * @param srcUser 原用户
     * @param targetUser 参照用户
     */
    public ViewObjCompare4Oracle(String srcUser, String targetUser) {
        super(srcUser, targetUser);
        getCommonModel().getTaskInfo().setTaskName("视图对象结构比较");
        getCommonModel().getTaskInfo().setTaskType(MetaType.VIEW_OBJ);
    }

    @Override
    public void execute() {
        beforeListener();
        Statement stmt = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            Map<String, List<AbstractObjectMeta>> lostAndExcessColl = queryViewObjLostAndExcess(stmt);
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
     * 查询视图对象缺失/多余明细
     * @param stmt SQL语句执行实例
     * @return rtnMap rtnMap
     * @throws SQLException 抛出异常做Log
     */
    private Map<String, List<AbstractObjectMeta>> queryViewObjLostAndExcess(Statement stmt) throws SQLException {
        String srcUser = getCommonModel().getTaskInfo().getSrcUser();
        String tarUser = getCommonModel().getTaskInfo().getTargetUser();
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(sqlTmpl.replaceFirst("@T", "2").replaceFirst("@SRC", srcUser).replaceFirst("@TAR", tarUser))
            .append(" UNION ALL ")
            .append(sqlTmpl.replaceFirst("@T", "1").replaceFirst("@SRC", tarUser).replaceFirst("@TAR", srcUser));
        ResultSet rs = stmt.executeQuery(sbuf.toString());
        List<AbstractObjectMeta> lostArr = new ArrayList<AbstractObjectMeta>();
        List<AbstractObjectMeta> excessArr = new ArrayList<AbstractObjectMeta>();
        while (rs.next()) {
            int type = rs.getInt("TYPE");
            AbstractObjectMeta tabInst = new AbstractObjectMeta(rs.getString("VIEW_NAME"), MetaType.VIEW_OBJ);
            if (MetaConstant.FLAG_LOST == type) {
                lostArr.add(tabInst);
            } else {
                excessArr.add(tabInst);
            }
        }
        Map<String, List<AbstractObjectMeta>> rtnMap = new HashMap<String, List<AbstractObjectMeta>>();
        rtnMap.put(MetaConstant.OBJ_LOST, lostArr);
        rtnMap.put(MetaConstant.OBJ_EXCESS, excessArr);
        return rtnMap;
    }
}
