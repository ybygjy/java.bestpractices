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
 * 负责Oracle表对象结构的比较
 * @author WangYanCheng
 * @version 2011-10-8
 */
public class TableObjCompare4Oracle extends AbstractTask {
	/**缺失/多余表对象SQL模版*/
	private static String tmplLAESql="SELECT '@T' TYPE, OWNER,TABLE_NAME,TABLESPACE_NAME, STATUS FROM ALL_TABLES ATT WHERE ATT.OWNER = '@SRC' AND NOT EXISTS (SELECT 1 FROM ALL_TABLES IATT WHERE IATT.OWNER='@TAR' AND IATT.TABLE_NAME = ATT.TABLE_NAME) ";
	/**表对象数量统计*/
	private static String tmplOCSql = "SELECT (SELECT COUNT(*) FROM ALL_TABLES WHERE OWNER = '@TAR') @TAR, (SELECT COUNT(*) FROM ALL_TABLES WHERE OWNER = '@SRC') @SRC FROM DUAL";
    /**
     * 构造器
     * @param srcUser 源用户
     * @param targetUser 参照用户
     */
    public TableObjCompare4Oracle(String srcUser, String targetUser) {
        super(srcUser, targetUser);
        getCommonModel().getTaskInfo().setTaskName("表对象结构比较");
        getCommonModel().getTaskInfo().setTaskType(MetaType.TABLE_OBJ);
    }
    /**
     * {@inheritDoc}
     * <p>Oracle数据库表对象结构比较</p>
     */
    public void execute() {
        beforeListener();
        Statement stmt = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            //统计表对象数量
            Map<String, String> tabCount = queryTableCount(stmt);
            //统计表对象缺失/多余明细
            getCommonModel().putRawData(MetaConstant.OBJ_COUNT, tabCount);
            getCommonModel().putRawData(MetaConstant.OBJ_LOSTANDEXCESS, queryLostAndExcess(stmt));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(stmt);
        }
        getCommonModel().getTaskInfo().setFinished(true);
        afterListener();
    }

    /**
     * 统计表数量
     * @param stmt sql语句执行实例
     * @return rtnMap rtnMap
     * @throws SQLException 抛出异常做Log
     */
    private Map<String, String> queryTableCount(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(tmplOCSql.replaceAll("@TAR", targetUser).replaceAll("@SRC", srcUser));
        Map<String, String> tmpMap = new HashMap<String, String>();
        String srcUserCount = "0", targetUserCount = "0";
        if (rs.next()) {
            srcUserCount = rs.getString(srcUser);
            targetUserCount = rs.getString(targetUser);
        }
        rs.close();
        rs = null;
        tmpMap.put(srcUser, srcUserCount);
        tmpMap.put(targetUser, targetUserCount);
        return tmpMap;
    }

    /**
     * 取缺失/多余表对象明细
     * @param stmt sql语句执行实例
     * @return rtnMap 值集
     * @throws SQLException 抛出异常做log
     */
    private Map<String, List<AbstractObjectMeta>> queryLostAndExcess(Statement stmt) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append(tmplLAESql.replaceFirst("@T", "2").replaceFirst("@SRC", srcUser).replaceFirst("@TAR", targetUser))
        .append("UNION ALL ")
        .append(tmplLAESql.replaceFirst("@T", "1").replaceFirst("@SRC", targetUser).replaceFirst("@TAR", srcUser))
        ;
        ResultSet rs = stmt.executeQuery(sql.toString());
        List<AbstractObjectMeta> lostList = new ArrayList<AbstractObjectMeta>();
        List<AbstractObjectMeta> excessList = new ArrayList<AbstractObjectMeta>();
        while (rs.next()) {
            int type = rs.getInt("TYPE");
            AbstractObjectMeta omoInst = new AbstractObjectMeta();
            omoInst.setObjectType(MetaType.TABLE_OBJ);
            omoInst.setObjectName(rs.getString("TABLE_NAME"));
            if (type == MetaConstant.FLAG_LOST) {
                lostList.add(omoInst);
            } else {
                excessList.add(omoInst);
            }
        }
        rs.close();
        rs = null;
        Map<String, List<AbstractObjectMeta>> rtnMap = new HashMap<String, List<AbstractObjectMeta>>();
        rtnMap.put(MetaConstant.OBJ_LOST, lostList);
        rtnMap.put(MetaConstant.OBJ_EXCESS, excessList);
        return rtnMap;
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        TableObjCompare4Oracle tc4oInst = new TableObjCompare4Oracle("NSTCSA581", "NSTCSA601");
        System.out.println(tc4oInst.getCommonModel().getTaskInfo().getTaskClassName());
    }
}
