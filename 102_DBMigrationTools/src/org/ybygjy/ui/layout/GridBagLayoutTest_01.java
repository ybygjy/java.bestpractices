package org.ybygjy.ui.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * GridBagLayout学习
 * @author WangYanCheng
 * @version 2012-9-3
 */
public class GridBagLayoutTest_01 extends JPanel {
    /** serial number*/
    private static final long serialVersionUID = 8306571575374384968L;
    /** search button*/
    private JButton searchBtn;
    /** 下拉选择框*/
    private JComboBox modeCombo;
    /** 标签*/
    private JLabel tagLbl;
    /** 标签*/
    private JLabel tagModeLbl;
    /** 预览标签*/
    private JLabel previewLbl;
    /** 表格*/
    private JTable resTable;
    /** 输入框*/
    private JTextField tagTxt;
    /**
     * Constructor
     */
    public GridBagLayoutTest_01() {
        GridBagLayout gridbag = new GridBagLayout();
        this.setLayout(gridbag);
        GridBagConstraints c = new GridBagConstraints();
        c.fill=GridBagConstraints.BOTH;
        c.weightx = 2;
        c.weighty = 1;
        c.insets = new Insets(5, 5, 5, 5);
        tagLbl = new JLabel("Tags");
        c.gridx = 0; //x grid position
        c.gridy = 0; //y grid position
        gridbag.setConstraints(tagLbl, c); //associate the label with a constraint object 
        this.add(tagLbl); //add it to content pane
        
        tagModeLbl = new JLabel("Tag Mode");
        c.gridx = 0;
        c.gridy = 1;
        gridbag.setConstraints(tagModeLbl, c);
        this.add(tagModeLbl);
        tagTxt = new JTextField("plinth");
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        gridbag.setConstraints(tagTxt, c);
        this.add(tagTxt);
        String[] options = {"all", "any"};
        modeCombo = new JComboBox(options);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(modeCombo, c);
        this.add(modeCombo);
        searchBtn = new JButton("Search");
        c.gridx = 1;
        c.gridy = 2;
        gridbag.setConstraints(searchBtn, c);
        this.add(searchBtn);
        resTable = new JTable(5,3);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        gridbag.setConstraints(resTable, c);
        this.add(resTable);
        previewLbl = new JLabel("Preview goes here");
        c.gridx = 0;
        c.gridy = 4;
        gridbag.setConstraints(previewLbl, c);
        this.add(previewLbl);
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JFrame jframeInst = new JFrame();
        Container contentPanel = jframeInst.getContentPane();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new GridBagLayoutTest_01(), BorderLayout.CENTER);
        jframeInst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframeInst.pack();
        jframeInst.setVisible(true);
    }
}
