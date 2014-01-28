package org.ybygjy.example;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ybygjy.MessageListener;
import org.ybygjy.logger.LoggerFactory;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.FileUtils;
import org.ybygjy.util.SysConstants;

/**
 * 依据表的行数更新序列，有如下步骤：
 * <p>
 * 1、查数据库中的序列
 * <p>
 * 2、分析序列名称提取表名称
 * <p>
 * 3、查询表行数
 * <p>
 * 4、更新序列值
 * <h4>有如下约束：</h4>
 * <p>
 * 1、要求序列名称是有规则的
 * @author WangYanCheng
 * @version 2012-5-31
 */
public class AutoIncrementSEQ2 {
    /** 与数据库的连接 */
    private Connection conn;
    /** 消息传递*/
    private MessageListener mlInst;
    /** 前缀 */
    private String[] seqREGPrefix = { "^S_", "^SEQ_" };
    /** 后缀 */
    private String[] seqREGSuffix = { "_SEQ$" };
    /** Database schema */
    private String ownerSchema;
    /** Logger */
    private static Logger logger;
    static {
        logger = LoggerFactory.getInstance().getLogger(AutoIncrementSEQ2.class.getName());
    }
    /**
     * Constructor
     * @param messageListener 
     */
    public AutoIncrementSEQ2(MessageListener messageListener) {
        this.mlInst = messageListener;
    }
    /**
     * Constructor
     * @param conn 与数据库的连接
     */
    public AutoIncrementSEQ2(Connection conn, String ownerSchema) {
        this.conn = conn;
        this.ownerSchema = ownerSchema;
    }

    /**
     * 查表行数
     * @param tableName 表名称
     * @return rtnNums：表的行数/0：表不存在或其它情况
     */
    public int qryTableNums(String tableName) {
        String tmplSQL = "SELECT COUNT(1) CC FROM ".concat(tableName);
        int rtnNums = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(tmplSQL);
            if (rs.next()) {
                rtnNums = rs.getInt(1);
            }
        } catch (Exception e) {
            rtnNums = SysConstants.DB_SEQ_DEFVAL;
            logger.log(Level.WARNING, "查表行数失败：".concat(tmplSQL), e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(stmt);
        }
        return rtnNums;
    }

    /**
     * 查序列
     * @return rtnSEQArr/null
     */
    public String[] qrySEQ() {
        String qrySEQ = "SELECT SEQUENCE_NAME, INCREMENT_BY FROM USER_SEQUENCES A WHERE NOT EXISTS (SELECT 1 FROM USER_DEPENDENCIES B WHERE B.REFERENCED_OWNER = ? AND B.TYPE = 'TRIGGER' AND B.REFERENCED_TYPE = 'SEQUENCE' AND B.REFERENCED_NAME = A.SEQUENCE_NAME)";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String[] rtnArray = null;
        try {
            pstmt = conn.prepareStatement(qrySEQ);
            pstmt.setString(1, this.ownerSchema);
            rs = pstmt.executeQuery();
            List<String> tmpList = new ArrayList<String>();
            while (rs.next()) {
                tmpList.add(rs.getString(1));
                tmpList.add(rs.getString(2));
            }
            rtnArray = tmpList.toArray(new String[1]);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "查序列失败", e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
        return rtnArray;
    }

    /**
     * 分析提取表名称
     * @param seqArray 序列集
     * @return tableNameArray 表名称集合
     */
    public String[] analyseTableName(String[] seqArray) {
        String[] tableNameArray = new String[seqArray.length];
        for (int index = tableNameArray.length; index >= 0; index--) {
            String seqName = seqArray[index];
            tableNameArray[index] = analyseTableName(seqName);
            System.out.println("表：".concat(tableNameArray[index]).concat(":").concat(seqName));
        }
        return tableNameArray;
    }

    /**
     * 重置序列
     * @param seqName 序列名称
     * @param newSeqValue 新序列值
     * @param oldSeqValue <strong>原始序列自增值</strong>
     */
    public void resetSEQ(String seqName, int newSeqValue, int oldSeqValue) {
        String tmpSql1 = "ALTER SEQUENCE @SEQ INCREMENT BY @V";
        String tmpSql2 = "SELECT @SEQ.nextval from dual";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(tmpSql1.replaceFirst("@SEQ", seqName).replaceFirst("@V",
                String.valueOf(newSeqValue - 1)));
            stmt.execute(tmpSql2.replaceFirst("@SEQ", seqName));
            stmt.execute(tmpSql1.replaceFirst("@SEQ", seqName).replaceFirst("@V",
                String.valueOf(oldSeqValue)));
            logger.info("序列重置完成：".concat(seqName).concat("：").concat(String.valueOf(oldSeqValue)).concat(">>")
                .concat(String.valueOf(newSeqValue)));
        } catch (Exception e) {
            logger.log(Level.WARNING,
                "序列重置失败：".concat(seqName).concat("：").concat(String.valueOf(newSeqValue)).concat("：")
                    .concat(String.valueOf(oldSeqValue)), e);
        } finally {
            DBUtils.close(stmt);
        }
    }

    /**
     * 提取表名称
     * @param seqName 序列名称
     * @return tableName 表名称
     */
    private String analyseTableName(String seqName) {
        String tableName = null;
        for (String tmpStr : seqREGPrefix) {
            tableName = tableName == null ? seqName.replaceFirst(tmpStr, "") : tableName.replaceFirst(
                tmpStr, "");
        }
        for (String tmpStr : seqREGSuffix) {
            tableName = tableName == null ? seqName.replaceFirst(tmpStr, "") : tableName.replaceFirst(
                tmpStr, "");
        }
        return tableName;
    }

    /**
     * 调用入口(硬编码)
     * @param seqArray 序列串
     */
    public void doWork(String[] seqArray) {
        int index = seqArray.length - 1;
        for (; index >= 0; index--) {
            if (index % 2 == 0) {
                String seqName = seqArray[index];
                String tableName = analyseTableName(seqName);
                int tableNums = qryTableNums(tableName);
                tableNums = tableNums <= 0 ? 1 : tableNums;
                resetSEQ(seqName, tableNums * 2, Integer.parseInt(seqArray[index + 1]));
            }
        }
    }
    /**
     * 调用入口(普通)
     * @param seqArray 序列串
     */
    public void doWorkCommon(String[] seqArray) {
        int index = seqArray.length - 1;
        for (; index >= 0; index--) {
                String seqName = seqArray[index];
                String tableName = analyseTableName(seqName);
                int tableNums = qryTableNums(tableName);
                tableNums = tableNums <= 0 ? 1 : tableNums;
                resetSEQ(seqName, tableNums * 2, 1);
        }
    }

    /**
     * @return the mlInst
     */
    public MessageListener getMessageListener() {
        return mlInst;
    }

    /**
     * @param mlInst the mlInst to set
     */
    public void setMessageListener(MessageListener mlInst) {
        this.mlInst = mlInst;
    }

    /**
     * 创建实例
     * @param connURL 与数据库的连接
     * @param seqPath 序列路径
     * @return ais2Inst {@link AutoIncrementSEQ2}
     */
    public void doWork(String connURL, String seqPath) {
        if (this.getMessageListener() != null) {
            this.getMessageListener().beforeListener();
        }
        File dirInst = new File(seqPath);
        if (dirInst == null || !dirInst.exists()) {
            logger.log(Level.WARNING, "序列目录地址错误 ".concat(seqPath));
        }
        String[] fileNames = FileUtils.fetchFileName(dirInst);
        Connection conn = null;
        try {
            conn = DBUtils.createConn4Oracle(connURL);
            String ownerSchema = DBUtils.getSchema(conn);
            if (ownerSchema != null) {
                AutoIncrementSEQ2 ais2Inst = new AutoIncrementSEQ2(conn, ownerSchema);
                ais2Inst.doWorkCommon(fileNames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn);
        }
        if (this.getMessageListener() != null) {
            this.getMessageListener().afterListener();
        }
    }
}
