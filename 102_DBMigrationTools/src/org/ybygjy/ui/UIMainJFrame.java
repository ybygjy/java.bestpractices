package org.ybygjy.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.ybygjy.MessageListener;
import org.ybygjy.example.AutoIncrementSEQ2;
import org.ybygjy.logger.LoggerFactory;
import org.ybygjy.util.SysConstants;

/**
 * 主界面
 * @author WangYanCheng
 * @version 2012-4-13
 */
public class UIMainJFrame extends javax.swing.JFrame implements ActionListener, MessageListener {
    /** serialNumber */
    private static final long serialVersionUID = -4951738984407042651L;
    /** Logger Instance */
    private Logger logger;
    private javax.swing.JButton execBtn;
    private javax.swing.JTextArea execLogArea;
    private javax.swing.JLabel srcConnUrlLabel;
    private javax.swing.JLabel tarConnUrlLabel;
    private javax.swing.JLabel tableSrcPathLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanel2;
    private javax.swing.JPanel exeLogPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField srcConnUrl;
    private javax.swing.JTextField tarConnUrl;
    private javax.swing.JTextField tableSrcPath;
    private javax.swing.JButton resetSEQBtn;
    private javax.swing.JFileChooser jfcComp;
    private javax.swing.JButton fbcmBtn;

    /** Creates new form UIMainJFrame */
    public UIMainJFrame() {
        initLogger();
        initComponents();
        logger.info("启动数据迁移工具。。。输出日志文件：".concat(LoggerFactory.getLogFilePath()));
    }

    private void initLogger() {
        // 注册LogHandler
        LoggerFactory.getInstance().addLoggerHandler(new InnerLogHandler());
        logger = LoggerFactory.getInstance().getLogger(this.getClass().getName());
    }

    /**
     * 初始化界面组件布局结构
     */
    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        srcConnUrlLabel = new javax.swing.JLabel();
        tarConnUrlLabel = new javax.swing.JLabel();
        srcConnUrl = new javax.swing.JTextField();
        tarConnUrl = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        tableSrcPathLabel = new javax.swing.JLabel();
        tableSrcPath = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        execBtn = new javax.swing.JButton();
        resetSEQBtn = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        mainPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        exeLogPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        execLogArea = new javax.swing.JTextArea();
        fbcmBtn = new javax.swing.JButton();
        jfcComp = new javax.swing.JFileChooser(".");
        jfcComp.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("数据迁移工具");

        srcConnUrlLabel.setText("源系统DB连接串：");

        tarConnUrlLabel.setText("目标系统DB连接串：");
        srcConnUrl.setText(SysConstants.DB_URL_ORACLE);
        tarConnUrl.setText(SysConstants.DB_URL_ORACLE);

        tableSrcPathLabel.setText("表结构目录：");

        tableSrcPath.setText("C:\\KettleLog\\");

        execBtn.setText("执行");
        execBtn.setActionCommand("executeBtn");
        execBtn.addActionListener(this);
        resetSEQBtn.setText("重置数据库序列");
        resetSEQBtn.setActionCommand("resetSEQBtn");
        resetSEQBtn.addActionListener(this);
        fbcmBtn.setText("选择");
        fbcmBtn.setActionCommand("chooiseDir4FBCM");
        fbcmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UIMainJFrame.this.showOpenDialog();
            }
        });
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(mainPanelLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(
                mainPanelLayout
                    .createSequentialGroup()
                    .addGroup(
                        mainPanelLayout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                mainPanelLayout
                                    .createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(
                                        mainPanelLayout
                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(
                                                mainPanelLayout.createSequentialGroup()
                                                    .addComponent(srcConnUrlLabel).addGap(18, 18, 18)
                                                    .addComponent(srcConnUrl))
                                            .addGroup(
                                                mainPanelLayout.createSequentialGroup()
                                                    .addComponent(tarConnUrlLabel).addGap(18, 18, 18)
                                                    .addComponent(tarConnUrl))
                                            .addGroup(
                                                mainPanelLayout.createSequentialGroup()
                                                    .addComponent(tableSrcPathLabel).addGap(18, 18, 18)
                                                    .addComponent(fbcmBtn).addGap(1, 1, 1)
                                                    .addComponent(tableSrcPath))))
                            .addGroup(
                                mainPanelLayout.createSequentialGroup().addGap(18, 18, 18)
                                    .addComponent(execBtn).addGap(18, 18, 18).addComponent(resetSEQBtn)
                                    .addGap(0, 0, Short.MAX_VALUE))).addContainerGap())
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING));
        mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            mainPanelLayout
                .createSequentialGroup()
                .addContainerGap()
                .addGroup(
                    mainPanelLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(srcConnUrlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(srcConnUrl, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(
                    mainPanelLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tarConnUrlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tarConnUrl, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(
                    mainPanelLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tableSrcPathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fbcmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tableSrcPath, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                // .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(
                    mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(execBtn).addComponent(resetSEQBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setViewportView(execLogArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(exeLogPanel);
        exeLogPanel.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1,
            javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1,
            javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

        jTabbedPane2.addTab("日志", exeLogPanel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(mainPanel2);
        mainPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTabbedPane2));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            jPanel2Layout.createSequentialGroup().addComponent(jTabbedPane2).addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout
            .setHorizontalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainPanel2, javax.swing.GroupLayout.Alignment.TRAILING,
                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 214,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(mainPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

        pack();
    }

    /**
     * 呈现文件选择框
     * @return rtnFilePath 文件/文件夹路径
     */
    private String showOpenDialog() {
        String rtnStr = null;
        int flag = jfcComp.showOpenDialog(UIMainJFrame.this);
        if (JFileChooser.APPROVE_OPTION == flag) {
            rtnStr = jfcComp.getSelectedFile().getAbsolutePath();
        }
        this.tableSrcPath.setText(rtnStr);
        return rtnStr;
    }

    private void doWork() {
        String tmpStr = tableSrcPath.getText();
        if (tmpStr != null && tmpStr.matches(pathVerifyStr)) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            new UILogicAdapter(srcConnUrl.getText(), tarConnUrl.getText(), UIMainJFrame.this,
                                tableSrcPath.getText()).doWork();
                        }
                    }).start();
                }
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("executeBtn".equals(e.getActionCommand())) {
            execBtn.setEnabled(false);
            this.doWork();
        } else if ("resetSEQBtn".equals(e.getActionCommand())) {
            resetSEQBtn.setEnabled(false);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            new AutoIncrementSEQ2(new MessageListener() {
                                @Override
                                public void beforeListener() {
                                    logger.info("序列任务开始处理");
                                }
                                @Override
                                public void afterListener() {
                                    logger.info("序列任务处理结束");
                                    resetSEQBtn.setEnabled(true);
                                }
                            }).doWork(tarConnUrl.getText(), tableSrcPath.getText());
                        }
                    }).start();
                }
            });
        }
    }

    @Override
    public void beforeListener() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        logger.info("开始执行数据迁移。。。");
    }

    @Override
    public void afterListener() {
        logger.info("完成数据迁移。。。");
        execBtn.setEnabled(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** 路径验证正则表达式 */
    private String pathVerifyStr = "^(\\w|/)[^\\^]+$";

    /**
     * InnerLogHandler
     * @author WangYanCheng
     * @version 2012-4-16
     */
    private class InnerLogHandler extends Handler {
        @Override
        public void publish(final LogRecord record) {
            if (UIMainJFrame.this.execLogArea.getLineCount() > 2000) {
                UIMainJFrame.this.execLogArea.setText("");
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    UIMainJFrame.this.execLogArea.append(record.getMessage().concat("\n"));
                }
            });
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }

        @Override
        public void setEncoding(String encoding) throws SecurityException, UnsupportedEncodingException {
            super.setEncoding("GB18030");
        }
    }
}
