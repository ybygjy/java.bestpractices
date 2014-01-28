package org.ybygjy.ui.layout;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 体验BorderLayout
 * @author WangYanCheng
 * @version 2012-8-31
 */
public class BorderLayoutTest extends JPanel {
    /**
     * serial number
     */
    private static final long serialVersionUID = 4085404991566757239L;

    /**
     * 构造函数
     */
    public BorderLayoutTest() {
        this.setLayout(new BorderLayout());
        this.add(new JButton("North"), BorderLayout.NORTH);
        this.add(new JButton("South"), BorderLayout.SOUTH);
        this.add(new JButton("East"), BorderLayout.EAST);
        this.add(new JButton("West"), BorderLayout.WEST);
        this.add(new JButton("Center"), BorderLayout.CENTER);
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        jframe.setContentPane(new BorderLayoutTest());
        jframe.pack();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }
}
