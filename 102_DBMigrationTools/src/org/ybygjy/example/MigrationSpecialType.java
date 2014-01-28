package org.ybygjy.example;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.ybygjy.logger.LoggerFactory;
import org.ybygjy.util.DBUtils;
import org.ybygjy.util.SysConstants;

/**
 * 迁移大字段
 * @author WangYanCheng
 * @version 2012-11-22
 */
public class MigrationSpecialType implements ActionListener {
    private JTextField sqlConnUrl;
    private JTextField oraConnUrl;
    private JTextField tableName;
    private JTextField sColumnName;
    private JTextField primaryKeyColumn;
    public void createUI() {
        JFrame jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        createField(mainPanel);
        jframe.getContentPane().add(mainPanel);
        jframe.pack();
        jframe.setVisible(true);
    }
    private void createField(JPanel mainPanel) {
        GridBagLayout gblInst = new GridBagLayout();
        mainPanel.setLayout(gblInst);
        GridBagConstraints gbcInst = new GridBagConstraints();
        JLabel sqlConnLabel = new JLabel("SQLServer连接串:");
        gbcInst.fill = GridBagConstraints.BOTH;
        gbcInst.gridx = 0;
        gbcInst.gridy = 0;
        gblInst.setConstraints(sqlConnLabel, gbcInst);
        mainPanel.add(sqlConnLabel);
        sqlConnUrl = new JTextField(20);
        gbcInst.gridx = 1;
        gbcInst.weightx = 0.7;
        gblInst.setConstraints(sqlConnUrl, gbcInst);
        mainPanel.add(sqlConnUrl);
        JLabel oraConnLabel = new JLabel("Oracle连接串:");
        gbcInst.fill = GridBagConstraints.BOTH;
        gbcInst.gridx = 0;
        gbcInst.gridy = 1;
        gblInst.setConstraints(oraConnLabel, gbcInst);
        mainPanel.add(oraConnLabel);
        oraConnUrl = new JTextField(20);
        gbcInst.gridx = 1;
        gbcInst.weightx = 0.7;
        gblInst.setConstraints(oraConnUrl, gbcInst);
        mainPanel.add(oraConnUrl);
        JLabel tableLabel = new JLabel("表名称:");
        gbcInst.fill = GridBagConstraints.BOTH;
        gbcInst.gridx = 0;
        gbcInst.gridy = 2;
        gblInst.setConstraints(tableLabel, gbcInst);
        mainPanel.add(tableLabel);
        tableName = new JTextField(20);
        gbcInst.gridx = 1;
        gbcInst.weightx = 0.7;
        gblInst.setConstraints(tableName, gbcInst);
        mainPanel.add(tableName);
        JLabel columnLabel = new JLabel("特殊字段列:");
        gbcInst.fill = GridBagConstraints.BOTH;
        gbcInst.gridx = 0;
        gbcInst.gridy = 3;
        gblInst.setConstraints(columnLabel, gbcInst);
        mainPanel.add(columnLabel);
        sColumnName = new JTextField(20);
        gbcInst.gridx = 1;
        gbcInst.weightx = 0.7;
        gblInst.setConstraints(sColumnName, gbcInst);
        mainPanel.add(sColumnName);
        JLabel primaryKeyColumnLabel = new JLabel("主键:");
        gbcInst.fill = GridBagConstraints.BOTH;
        gbcInst.gridx = 0;
        gbcInst.gridy = 4;
        gblInst.setConstraints(primaryKeyColumnLabel, gbcInst);
        mainPanel.add(primaryKeyColumnLabel);
        primaryKeyColumn = new JTextField(20);
        gbcInst.gridx = 1;
        gbcInst.weightx = 0.7;
        gblInst.setConstraints(primaryKeyColumn, gbcInst);
        mainPanel.add(primaryKeyColumn);
        sqlConnUrl.setText(SysConstants.DB_URL_SQLSERVER);
        oraConnUrl.setText(SysConstants.DB_URL_ORACLE);
        tableName.setText("EDC_MESSAGE");
        sColumnName.setText("CONTENT");
        primaryKeyColumn.setText("ID");
        JButton jbtn = new JButton("执行");
        gbcInst.gridy = 5;
        gbcInst.gridx = 0;
        gbcInst.gridwidth = 2;
        gbcInst.weightx = 0.7;
        gblInst.setConstraints(jbtn, gbcInst);
        mainPanel.add(jbtn);
        jbtn.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Connection conn4Ora = null;
        Connection conn4SqlServer = null;
        try {
            conn4Ora = DBUtils.createConn4Oracle(oraConnUrl.getText());
            conn4SqlServer = DBUtils.createConn4MSSql(sqlConnUrl.getText());
            InnerMigrationSpecialType mstInst = new InnerMigrationSpecialType(conn4Ora, conn4SqlServer, tableName.getText(), tableName.getText());
            mstInst.querySqlServer(sColumnName.getText(), primaryKeyColumn.getText());
        } catch (SQLException ee) {
            ee.printStackTrace();
        } finally {
            DBUtils.close(conn4SqlServer);
            DBUtils.close(conn4Ora);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                new MigrationSpecialType().createUI();
            }
        });
    }
    
    public static void doWork(Connection conn4Ora, Connection conn4SqlServer, String srcTableName,
                              String targetTableName, String columnName, String keyColumn)
        throws SQLException {
        InnerMigrationSpecialType mstInst = new InnerMigrationSpecialType(conn4Ora, conn4SqlServer,
            srcTableName, targetTableName);
        mstInst.querySqlServer(columnName, keyColumn);
    }
}
/**
 * 迁移特殊类型字段
 * @author WangYanCheng
 * @version 2012-9-3
 */
class InnerMigrationSpecialType {
    /** 与Oracle数据库连接的对象 */
    private Connection conn4Ora;
    /** 与SQL Server数据库连接的对象 */
    private Connection conn4SqlServer;
    /** 源表名称 */
    private String srcTableName;
    /** 目标表名称 */
    private String targetTableName;
    /**日志对象*/
    private Logger logger = LoggerFactory.getInstance().getLogger();

    /**
     * Constructor
     * @param conn4Ora Oracle数据库连接
     * @param conn4SqlServer SqlServer数据库连接
     */
    public InnerMigrationSpecialType(Connection conn4Ora, Connection conn4SqlServer, String srcTableName,
        String targetTableName) {
        this.conn4Ora = conn4Ora;
        this.conn4SqlServer = conn4SqlServer;
        this.srcTableName = srcTableName;
        this.targetTableName = targetTableName;
    }

    /**
     * 查询某个给定表名称的数据，对每行数据进行处理
     * @param columnName 特殊字段列名称
     * @param primaryKeyColumn 主键列名称
     * @throws SQLException {@link SQLException}
     */
    public void querySqlServer(String columnName, String primaryKeyColumn) throws SQLException {
        String sqlTMPL = "SELECT A.@PK,A.CONTENT FROM @T A";
        sqlTMPL = sqlTMPL.replaceAll("@PK", primaryKeyColumn).replaceAll("@T", this.srcTableName);
        PreparedStatement pstmt = null;
        try {
            pstmt = this.conn4SqlServer.prepareStatement(sqlTMPL);
            ResultSet rsInst = pstmt.executeQuery();
            int count = 1;
            while (rsInst.next()) {
                logger.info("处理第".concat(String.valueOf(count++)).concat("条。"));
                migration2Oracle(rsInst, columnName, primaryKeyColumn);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (null != pstmt) {
                DBUtils.close(pstmt);
            }
        }
    }

    /**
     * 迁移sqlServer数据至Oracle
     * @param srcRS sqlServer数据集
     * @param columnName 列名称
     * @throws SQLException {@link SQLException}
     */
    public void migration2Oracle(ResultSet srcRS, String columnName, String primaryKeyColumn)
        throws SQLException {
        String sqlTMPL = "SELECT A.@C FROM @T A WHERE A.@K=? FOR UPDATE";
        sqlTMPL = sqlTMPL.replaceAll("@C", columnName).replaceAll("@T", this.targetTableName)
            .replaceAll("@K", primaryKeyColumn);
        String primaryKey = srcRS.getString(primaryKeyColumn);
        Clob clobInst = srcRS.getClob(columnName);
        this.conn4Ora.setAutoCommit(false);
        PreparedStatement pstmt = null;
        try {
            pstmt = this.conn4Ora.prepareStatement(sqlTMPL);
            pstmt.setString(1, primaryKey);
            ResultSet rsInst = pstmt.executeQuery();
            if (rsInst.next()) {
                logger.info("开始迁移：".concat(primaryKey));
                Clob targetInst = rsInst.getClob(1);
                if (null != targetInst) {
                    innerMigration(clobInst, targetInst);
                } else {
                    this.updateCLOBDataNew(clobInst, this.targetTableName, columnName, primaryKeyColumn, primaryKey);
                }
            }
            this.conn4Ora.commit();
        } catch (SQLException e) {
            this.conn4Ora.rollback();
            this.logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            this.conn4Ora.rollback();
            this.logger.info(e.getMessage());
            e.printStackTrace();
        } finally {
            this.conn4Ora.setAutoCommit(true);
            if (null != pstmt) {
                pstmt.close();
            }
        }
    }

    /**
     * 测试更新某个表的某个clob字段值
     * <p>1、<strong>必须确认这行记录确实存在，如果不存在则明显浪费资源</strong>
     * <p>2、该方法的事务控制在外层
     * @param srcClob 源Clob
     * @param tableName 表名称
     * @param clobCol clob类型列
     * @param keyCol 主键列
     * @param keyValue 主键值
     * @throws SQLException {@link SQLException}
     */
    public void updateCLOBDataNew(Clob srcClob, String tableName, String clobCol, String keyCol, String keyValue) throws SQLException {
        String sqlTmpl = "UPDATE @T A SET A.@CC=? WHERE A.@PK=@PV";
        sqlTmpl = sqlTmpl.replaceAll("@T", tableName).replaceAll("@CC", clobCol).replaceAll("@PK", keyCol).replaceAll("@PV", keyValue);
        PreparedStatement pstmt = null;
        try {
            pstmt = this.conn4Ora.prepareStatement(sqlTmpl);
            Clob clob = this.conn4Ora.createClob();
            pstmt.setClob(1, clob);
            try {
                innerMigration(srcClob, clob);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pstmt.executeUpdate();
        } catch (SQLException sqle) {
            logger.warning(sqle.getMessage().concat("\n").concat(sqlTmpl));
            sqle.printStackTrace();
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * 两个clob流的内容copy
     * @param srcClob 源CLOB
     * @param tarClob 目标CLOB
     * @throws SQLException {@link SQLException}
     * @throws IOException {@link IOException}
     */
    private void innerMigration(Clob srcClob, Clob tarClob) throws SQLException, IOException {
        tarClob.truncate(0);
        Reader readerInst = srcClob.getCharacterStream();
        Writer writerInst = tarClob.setCharacterStream(1);
        char[] charBuff = new char[4 * 1024];
        int flags = -1;
        try {
            while ((flags = readerInst.read(charBuff)) != -1) {
                writerInst.write(charBuff, 0, flags);
            }
            writerInst.flush();
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (readerInst != null) {
                try {
                    readerInst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writerInst != null) {
                try {
                    writerInst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
//    public static void main(String[] args) {
//        Connection conn4Ora = null;
//        Connection conn4SqlServer = null;
//        try {
//            conn4Ora = DBUtils.createConn4Oracle(SysConstants.DB_URL_ORACLE);
//            conn4SqlServer = DBUtils.createConn4MSSql(SysConstants.DB_URL_SQLSERVER);
//            InnerMigrationSpecialType mstInst = new InnerMigrationSpecialType(conn4Ora, conn4SqlServer, "EDC_MESSAGE", "EDC_MESSAGE");
//            mstInst.querySqlServer("CONTENT", "ID");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBUtils.close(conn4SqlServer);
//            DBUtils.close(conn4Ora);
//        }
//    }
}
