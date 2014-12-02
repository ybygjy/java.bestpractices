package org.ybygjy.pattern.proxy.virtual.imageLoad;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * TestDriver
 * @author WangYanCheng
 * @version 2011-1-13
 */
public class TestDriver {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        String tmpUrl = "http://avatar.csdn.net/3/3/9/1_ybygjy.jpg";
        JFrame frame = new JFrame("CD Cover..");
        URL imageURL = null;
        ImageProxy imageProxy = null;
        try {
            imageURL = new URL(tmpUrl);
            imageProxy = new ImageProxy(imageURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel();
        frame.getContentPane().add(label);
        label.setIcon(imageProxy);
        frame.setBounds(0, 0, 300, 300);
        frame.setVisible(true);
    }
}
