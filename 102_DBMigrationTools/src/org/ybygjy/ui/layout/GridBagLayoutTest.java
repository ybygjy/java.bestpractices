package org.ybygjy.ui.layout;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * 体验GridBagLayout布局
 * @author WangYanCheng
 * @version 2012-8-31
 */
public class GridBagLayoutTest extends JPanel {
    /**
     * serial number
     */
    private static final long serialVersionUID = -5972490581294739882L;

    /**
     * Constructor
     */
    public GridBagLayoutTest() {
        this.setBorder(new TitledBorder(this.getClass().getName()));
        initComponent(this);
    }

    /**
     * 初始面板组件
     * @param contentPane 主面板容器
     */
    private void initComponent(Container contentPane) {
        GridBagLayout gblInst = new GridBagLayout();
        GridBagConstraints gbcInstHorizontal = new GridBagConstraints(GridBagConstraints.RELATIVE,
            GridBagConstraints.RELATIVE, 1, 1, 0, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints gbcInstVertical = new GridBagConstraints(GridBagConstraints.RELATIVE,
            GridBagConstraints.RELATIVE, 1, 1, 0, 0, GridBagConstraints.CENTER,
            GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0);
        contentPane.setLayout(gblInst);
        for (int i = 0; i < 10; i++) {
            JButton tmpBtn = new JButton("BTN_" + i);
            gblInst.setConstraints(tmpBtn, gbcInstHorizontal);
            JTextField tmpText = new JTextField("Text_" + i);
            gblInst.setConstraints(tmpText, gbcInstVertical);
            contentPane.add(tmpBtn);
            contentPane.add(tmpText);
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JFrame jframeInst = new JFrame();
        jframeInst.setContentPane(new GridBagLayoutTest());
        jframeInst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframeInst.pack();
        jframeInst.setVisible(true);
    }
}
