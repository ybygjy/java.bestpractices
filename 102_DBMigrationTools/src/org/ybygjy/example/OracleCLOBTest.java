package org.ybygjy.example;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ybygjy.util.DBUtils;
import org.ybygjy.util.FileUtils;
import org.ybygjy.util.SysConstants;

/**
 * 对CLOB数据类型的处理
 * <p>1、对CLOB列的添加、修改
 * <p>2、整理CLOB插入、更新的基本操作
 * <p>2.1、插入/更新，考虑兼容JDBC3.0
 * <p>2.2、使用JDBC4.0特性
 * @author WangYanCheng
 * @version 2012-8-30
 */
public class OracleCLOBTest {
    /** 与数据库的连接 */
    private Connection conn;
    /** 测试表名 */
    private String tableName = "CLOB_TABLE";

    /**
     * 构造函数
     * @param conn {@link Connection}
     */
    public OracleCLOBTest(Connection conn) {
        this.conn = conn;
    }
    /**
     * 测试插入
     * <p>1、注意Oracle的JDBC驱动为ojdbc6.jar
     * <p>2、考虑clob.setString方式在内容非常大的时候改成流输入的方式
     * @throws SQLException
     */
    public void insertDateNew() throws SQLException {
        String sql = "INSERT INTO ".concat(tableName).concat(" (ID,CONTENT, BLOB_CONTENT) VALUES(?,?,?)");
        PreparedStatement pstmt = null;
        try {
            this.conn.setAutoCommit(false);
            pstmt = this.conn.prepareStatement(sql);
            for (int i = 1; i < 50; i++) {
                pstmt.setString(1, String.valueOf(i));
                Clob clob = this.conn.createClob();
                clob.setString(1, "HelloWorld " + i);
                pstmt.setClob(2, clob);
                Blob blob = this.conn.createBlob();
                OutputStream ousInst = blob.setBinaryStream(1);
                this.writeBLOBContent(ousInst);
                pstmt.setBlob(3, blob);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            this.conn.commit();
        } catch (SQLException e) {
            this.conn.rollback();
            e.printStackTrace();
        } finally {
            this.conn.setAutoCommit(true);
            DBUtils.close(pstmt);
        }
    }
    /**
     * 负责向流中写入测试数据
     * @param ous {@link OutputStream}
     */
    public void writeBLOBContent(OutputStream ous) {
        BufferedInputStream bisInst = null;
        byte[] buff = new byte[1024 * 1024];
        try {
            bisInst = new BufferedInputStream(FileUtils.getClassStream(this.getClass().getName(), this.getClass().getClassLoader()));
            int flag = -1;
            while ((flag = bisInst.read(buff)) != -1) {
                ous.write(buff, 0, flag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bisInst) {
                try {
                    bisInst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                ous.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试插入CLOB
     * <p>1、首先开启事务，保证插入和更新在同一个会话中
     * <p>2、插入数据时CLOB字段内容先设置为空（EMPTY_CLOB）
     * <p>3、插入完成后查询插入的数据并更新CLOB列的值（利用FOR UPDATE特性）
     */
    public void insertData() throws SQLException {
        String sql = "INSERT INTO ".concat(tableName).concat(" (ID,CONTENT,BLOB_CONTENT) VALUES(?,EMPTY_CLOB(),?)");
        PreparedStatement pstmt = null;
        try {
            this.conn.setAutoCommit(false);
            pstmt = this.conn.prepareStatement(sql);
            for (int i = 1; i < 50; i++) {
                pstmt.setString(1, String.valueOf(i));
                InputStream ins = FileUtils.getClassStream(this.getClass().getName(), this.getClass().getClassLoader());
                pstmt.setBlob(2, ins);
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            this.updateInsertCLOB();
            this.conn.commit();
        } catch (SQLException e) {
            this.conn.rollback();
            e.printStackTrace();
        } finally {
            this.conn.setAutoCommit(true);
            DBUtils.close(pstmt);
        }
    }

    /**
     * 支持JDBC4.0之前处理插入CLOB
     * @param pstmt {@link PreparedStatement}
     * @throws SQLException
     */
    private void updateInsertCLOB() throws SQLException {
        String sql = "SELECT ID,CONTENT FROM ".concat(tableName);
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = this.conn.prepareStatement(sql);
            resultSet = pstmt.executeQuery(sql);
            while (resultSet.next()) {
                Clob clobInst = resultSet.getClob(2);
                Writer writerInst = clobInst.setCharacterStream(1);
                writerInst.write("HelloWorld " + resultSet.getString(1));
                writerInst.close();
            }
        } catch (IOException e) {
            throw new SQLException(e.getCause());
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * 测试查询CLOB
     */
    public void queryDate() throws SQLException {
        String sql = "SELECT * FROM ".concat(tableName);
        PreparedStatement pstmt = null;
        try {
            pstmt = this.conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            List<Map<String, Object>> rtnList = DBUtils.extractData(rs);
            for (Map<String, Object> valueEntity : rtnList) {
                System.out.println(valueEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * 测试更新CLOB
     * <p>注意，当Clob列为空无内容时，结果集rs.getClob(2)返回null
     * <p>对上述情况考虑采用先更新为EMPTY_CLOB()再更新成具体clob数据
     * <p>在jdbc6中可直接采用更新时使用clob完成（使用conn创建clob对象，直接传递clob）
     */
    public void updateClobDataOld() throws SQLException {
        String sql = "SELECT * FROM ".concat(tableName).concat(" WHERE ID=? FOR UPDATE");
        PreparedStatement pstmt = null;
        try {
            this.conn.setAutoCommit(false);
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, "1");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Clob clobInst = rs.getClob(2);
                clobInst.truncate(0);
                Writer writerInst = clobInst.setCharacterStream(1);
                try {
                    writerInst.write("BBC");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        writerInst.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.conn.commit();
        } catch (SQLException e) {
            this.conn.rollback();
            e.printStackTrace();
        } finally {
            this.conn.setAutoCommit(true);
            DBUtils.close(pstmt);
        }
    }

    /**
     * 测试更新CLOB
     */
    public void updateClobDataOld2() throws SQLException {
        String sql = "SELECT * FROM ".concat(tableName).concat(" WHERE ID=? FOR UPDATE");
        PreparedStatement pstmt = null;
        try {
            this.conn.setAutoCommit(false);
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, "1");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                oracle.sql.CLOB clobInst = (oracle.sql.CLOB) rs.getClob(2);
                clobInst.truncate(0);
                clobInst.setString(1, "Hi");
            }
            this.conn.commit();
        } catch (SQLException e) {
            this.conn.rollback();
            e.printStackTrace();
        } finally {
            this.conn.setAutoCommit(true);
            DBUtils.close(pstmt);
        }
    }

    /**
     * 测试更新某个表的某个clob字段值
     * <p><strong>必须确认这行记录确实存在，如果不存在则明显浪费资源</strong>
     * @param tableName 表名称
     * @param clobCol clob类型列
     * @param keyCol 主键列
     * @param keyValue 主键值
     * @throws SQLException {@link SQLException}
     */
    public void updateCLOBDataNew(String tableName, String clobCol, String keyCol, String keyValue) throws SQLException {
        String sqlTmpl = "UPDATE @T A SET A.@CC=? WHERE A.@PK=@PV";
        sqlTmpl = sqlTmpl.replaceAll("@T", tableName).replaceAll("@CC", clobCol).replaceAll("@PK", keyCol).replaceAll("@PV", keyValue);
        PreparedStatement pstmt = null;
        try {
            this.conn.setAutoCommit(false);
            pstmt = this.conn.prepareStatement(sqlTmpl);
            Clob clob = this.conn.createClob();
            pstmt.setClob(1, clob);
            Writer writerInst = clob.setCharacterStream(1);
            try {
                writerInst.write("HelloWorld");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writerInst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pstmt.executeUpdate();
            this.conn.commit();
        } catch (SQLException sqle) {
            this.conn.rollback();
            sqle.printStackTrace();
        } finally {
            this.conn.setAutoCommit(true);
            DBUtils.close(pstmt);
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DBUtils.createConn4Oracle(SysConstants.DB_URL_ORACLE);
            OracleCLOBTest clobTest = new OracleCLOBTest(conn);
            //clobTest.insertDateNew();
            //clobTest.insertData();
            //clobTest.queryDate();
            //clobTest.updateDataOld();
            //clobTest.updateDataNew();
            clobTest.updateCLOBDataNew("EDC_MESSAGE", "CONTENT", "ID", "2");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn);
        }
    }
}
