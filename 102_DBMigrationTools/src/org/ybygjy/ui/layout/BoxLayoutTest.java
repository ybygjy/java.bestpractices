package org.ybygjy.ui.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * BoxLayout体验
 * @author WangYanCheng
 * @version 2012-8-31
 */
public class BoxLayoutTest extends JPanel {
    /**
     * serial Number
     */
    private static final long serialVersionUID = -3378895481428591635L;

    public BoxLayoutTest() {
        Box boxInst = Box.createVerticalBox();
        boxInst.add(this.demo1());
        boxInst.add(this.demo2());
        this.add(boxInst);
    }

    public JPanel demo1() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new TitledBorder("Demo_01"));
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        Box boxInstLeft = Box.createVerticalBox();
        Box boxInstRight = Box.createVerticalBox();
        for (int i = 0; i < 10; i++) {
            boxInstLeft.add(Box.createVerticalStrut(5));
            boxInstLeft.add(new JLabel("JLabel_" + i));
            boxInstRight.add(Box.createVerticalStrut(5));
            boxInstRight.add(new JTextField("JTextField_" + i));
        }
        contentPane.add(boxInstLeft);
        contentPane.add(boxInstRight);
        return contentPane;
    }

    public JPanel demo2() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new TitledBorder("Demo_02"));
        Box baseBox = Box.createHorizontalBox();
        contentPane.add(baseBox);
        Box vbox1 = Box.createVerticalBox();
        JButton btn1 = new JButton("Btn_01");
        JButton btn2 = new JButton("Btn_02");
        btn2.setMaximumSize(new Dimension(100, 150));
        vbox1.add(btn1);
        vbox1.add(btn2);
        vbox1.add(Box.createVerticalGlue());
        baseBox.add(vbox1);

        Box vbox2 = Box.createVerticalBox();
        JButton btn3 = new JButton("Btn_03");
        btn3.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn3.setMaximumSize(new Dimension(300, 50));
        vbox2.add(btn3);

        Box hBox = Box.createHorizontalBox();
        vbox2.add(hBox);

        Box vbox3 = Box.createVerticalBox();
        vbox3.add(Box.createVerticalStrut(50));
        vbox3.add(new JButton("Btn_04"));
        vbox3.add(Box.createVerticalStrut(50));
        hBox.add(vbox3);

        Box vbox4 = Box.createVerticalBox();
        vbox4.add(Box.createVerticalGlue());
        vbox4.add(new JButton("Btn_05"));
        hBox.add(vbox4);
        baseBox.add(vbox2);
        return contentPane;
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(new FlowLayout());
        jframe.getContentPane().add(new BoxLayoutTest().demo1());
        jframe.getContentPane().add(new BoxLayoutTest().demo2());
        jframe.pack();
        jframe.setVisible(true);
    }
}
