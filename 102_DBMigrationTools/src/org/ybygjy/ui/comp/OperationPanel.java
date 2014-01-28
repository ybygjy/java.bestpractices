package org.ybygjy.ui.comp;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.ybygjy.example.MigrationSpecialType;
import org.ybygjy.logger.LoggerFactory;
import org.ybygjy.ui.UIUtils;
import org.ybygjy.util.DBUtils;

/**
 * 操作面板，负责提供操作按钮
 * @author WangYanCheng
 * @version 2012-9-4
 */
public class OperationPanel extends JPanel implements ActionListener {
    /**
     * serial Number
     */
    private static final long serialVersionUID = 4506953241502078333L;
    /**执行Btn*/
    private JButton execBtn;
    /**停止Btn*/
    private JButton stopBtn;
    /**执行命令*/
    private static String execCommand = "execBtn";
    /**停止命令*/
    private static String stopCommand = "stopBtn";
    /**引用基础*/
    private MainPanel mainPanel;
    /**
     * Constructor
     */
    public OperationPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setBorder(new TitledBorder("操作栏"));
        GridBagLayout gblInst = new GridBagLayout();
        setLayout(gblInst);
        GridBagConstraints gbcInst = new GridBagConstraints();
        gbcInst.gridx = 0;
        gbcInst.gridy = 0;
        gbcInst.insets = new Insets(1, 5, 1, 5);
        UIUtils.makeJLabel(this, "操作类型：", gblInst, gbcInst);
        gbcInst.gridx = 1;
        JRadioButton insertType = new JRadioButton("插入", true);
        JRadioButton updateType = new JRadioButton("更新");
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(updateType);
        radioGroup.add(insertType);
        UIUtils.addGBLComp(this, insertType, gblInst, gbcInst);
        gbcInst.gridx = 2;
        UIUtils.addGBLComp(this, updateType, gblInst, gbcInst);
        gbcInst.gridx = 0;
        gbcInst.gridy = 1;
        execBtn = UIUtils.makeJButton(this, "执行", gblInst, gbcInst);
        execBtn.setActionCommand(execCommand);
        execBtn.addActionListener(this);
        gbcInst.gridx = 1;
        stopBtn = UIUtils.makeJButton(this, "停止", gblInst, gbcInst);
        stopBtn.setActionCommand(stopCommand);
        stopBtn.addActionListener(this);
    }
    /**内部线程*/
    private InnerThread innerThread;
    @Override
    public void actionPerformed(ActionEvent e) {
        String commandStr = e.getActionCommand();
        if (execCommand.equals(commandStr) && null != mainPanel) {
            this.execBtn.setEnabled(false);
            innerThread = new InnerThread(
                mainPanel.getComponentValue("dbinfo.srcConnURL"),
                mainPanel.getComponentValue("dbinfo.tarConnURL"),
                mainPanel.getComponentValue("tableinfo.tableNames"),
                mainPanel.getComponentValue("tableinfo.columnName"),
                mainPanel.getComponentValue("tableinfo.pkColumn"));
            innerThread.addCallback(new CallBack() {
                @Override
                public void doCallBack() {
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run() {
                            execBtn.setEnabled(true);
                            releaseThread();
                        }
                    });
                }
            });
            innerThread.start();
        } else if (stopCommand.equals(commandStr)) {
            if (null != innerThread) {
                releaseThread();
                this.execBtn.setEnabled(true);
            }
        }
    }

    /**
     * 清理任务线程
     */
    private void releaseThread() {
        if (null != innerThread) {
            if (innerThread.isAlive()) {
                this.innerThread.interrupt();
            }
            this.innerThread = null;
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JFrame jframeInst = new JFrame();
        jframeInst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframeInst.setLayout(new FlowLayout());
        jframeInst.getContentPane().add(new OperationPanel(null));
        jframeInst.pack();
        jframeInst.setVisible(true);
    }

    /**
     * 任务处理线程
     * @author WangYanCheng
     * @version 2012-9-11
     */
    class InnerThread extends Thread {
        private String tarConnURL;
        private String srcConnURL;
        private String tableNames;
        private String columnName;
        private String pkColumn;
        private Logger logger;
        private CallBack callBackInst;
        /**
         * Constructor
         * @param srcConnURL 源数据库地址
         * @param tarConnURL 目标数据库地址
         * @param tableNames 表名称
         * @param columnName 列
         * @param pkColumn 主键
         */
        public InnerThread(String srcConnURL, String tarConnURL, String tableNames, String columnName,
            String pkColumn) {
            this.srcConnURL = srcConnURL;
            this.tarConnURL = tarConnURL;
            this.tableNames = tableNames;
            this.columnName = columnName;
            this.pkColumn = pkColumn;
            this.logger = LoggerFactory.getInstance().getLogger();
        }

        /**
         * 执行任务
         */
        public void run() {
            Connection conn4Ora = null;
            Connection conn4SqlServer = null;
            try {
                logger.info("建立与Oracle的连接：".concat(this.tarConnURL));
                conn4Ora = DBUtils.createConn4Oracle(this.tarConnURL);
                if (null == conn4Ora) {
                    return;
                }
                logger.info("建立与SqlServer的连接：".concat(this.srcConnURL));
                conn4SqlServer = DBUtils.createConn4MSSql(this.srcConnURL);
                if (null == conn4SqlServer) {
                    return;
                }
                MigrationSpecialType.doWork(conn4Ora, conn4SqlServer, this.tableNames, this.tableNames, columnName, this.pkColumn);
                logger.info("操作完成。。。");
            } catch (Exception e) {
                this.logger.warning(e.getMessage());
            } finally {
                DBUtils.close(conn4SqlServer);
                DBUtils.close(conn4Ora);
                this.callBackInst.doCallBack();
            }
        }
        /**
         * 添加回调
         * @param callBack
         */
        public void addCallback(CallBack callBack) {
            this.callBackInst = callBack;
        }
    }
    interface CallBack {
        /**
         * 回调
         */
        void doCallBack();
    }
}
