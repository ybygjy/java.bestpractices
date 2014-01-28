package org.ybygjy.ui2.comp;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 * 数据库信息录入模型
 * <p>
 * 1、提供数据加信息录入UI
 * <p>
 * 2、提供存储数据库信息的模型
 * @author WangYanCheng
 * @version 2012-10-29
 */
public class DBInfo {
    /** 名称 */
    private String catalogName;

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setterCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    /**
     * 创建数据库信息录入UI面板
     * @return
     */
    public JPanel createUI() {
        JPanel rtnJPanel = new JPanel();
        rtnJPanel.setBorder(new TitledBorder(getCatalogName()));
        rtnJPanel.setLayout(new GridBagLayout());
        int gridy = 0, weighty = 0;
        addItem(rtnJPanel, "驱     动：", createDriverBox(), gridy++, weighty);
        addItem(rtnJPanel, "用户名：", new JTextField(), gridy++, weighty);
        addItem(rtnJPanel, "口     令：", new JPasswordField(), gridy++, weighty);
        addItem(rtnJPanel, "连接串：", new JTextField(), gridy++, weighty);
        return rtnJPanel;
    }

    /**
     * 驱动选择
     * @return jcbInst {@link JComboBox}
     */
    private JComboBox createDriverBox() {
        JComboBox jcbInst = new JComboBox();
        jcbInst.addItem("oracle.jdbc.driver.OracleDriver");
        jcbInst.addItem("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return jcbInst;
    }

    /**
     * 添加组件
     * @param panel 面板对象
     * @param labelName 呈现的元素名称
     * @param comp 元素对象
     * @param gridy {@link GridBagLayout} 所需纵坐标参数
     * @param weighty {@link GridBagLayout} 组件空间布局扩展权重
     * @return rtnGBC {@link GridBagLayout}
     */
    private GridBagConstraints addItem(JPanel panel, String labelName, Component comp, int gridy,
                                       int weighty) {
        GridBagConstraints gbcInst = new GridBagConstraints();
        gbcInst.gridwidth = 1;
        gbcInst.gridheight = 1;
        gbcInst.gridx = 0;
        gbcInst.gridy = gridy;
        gbcInst.weightx = 0;
        gbcInst.fill = GridBagConstraints.NORTHEAST;
        panel.add(new JLabel(labelName), gbcInst);
        gbcInst.gridx = 1;
        gbcInst.weightx = 1;
        if (weighty > 0) {
            gbcInst.weighty = weighty;
        }
        gbcInst.fill = GridBagConstraints.BOTH;
        panel.add(comp, gbcInst);
        return gbcInst;
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jframe = new JFrame();
                jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jframe.setSize(300, 400);
                jframe.setLayout(new FlowLayout());
                jframe.getContentPane().add(new DBInfo().createUI());
                jframe.setVisible(true);
            }
        });
    }
}
