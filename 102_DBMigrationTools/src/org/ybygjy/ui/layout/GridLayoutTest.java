package org.ybygjy.ui.layout;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * 体验GridLayout
 * @author WangYanCheng
 * @version 2012-8-31
 */
public class GridLayoutTest extends JPanel {
    /**
     * serial Number
     */
    private static final long serialVersionUID = -8600544156118397899L;

    /**
     * 构造函数
     */
    public GridLayoutTest() {
        this.setBorder(new TitledBorder(this.getClass().getName()));
        this.initComponent(this);
    }

    /**
     * 封装Frame内容的创建
     * @param containerPane 主窗体可理解为装载组件的主面板
     */
    private void initComponent(Container containerPane) {
        GridLayout gridLayoutInst = new GridLayout(3, 4);
        containerPane.setLayout(gridLayoutInst);
        for (int i = 0; i < 6; i++) {
            JButton jBtn = new JButton("Button_" + i);
            containerPane.add(jBtn);
            containerPane.add(new JTextField("Button_" + i));
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JFrame jframeInst = new JFrame();
        jframeInst.setContentPane(new GridLayoutTest());
        jframeInst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframeInst.pack();
        jframeInst.setVisible(true);
    }
}
