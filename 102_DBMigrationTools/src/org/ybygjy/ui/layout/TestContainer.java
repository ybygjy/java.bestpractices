package org.ybygjy.ui.layout;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

/**
 * »ã×ÜUI_²¼¾Ö²âÊÔ
 * @author WangYanCheng
 * @version 2012-8-31
 */
public class TestContainer {
    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        Container contentPane = jframe.getContentPane();
        contentPane.setLayout(new FlowLayout(5));
        contentPane.add(new BorderLayoutTest());
        contentPane.add(new BoxLayoutTest());
        contentPane.add(new GridBagLayoutTest());
        contentPane.add(new GridLayoutTest());
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(800, 600);
        jframe.setVisible(true);
    }
}
