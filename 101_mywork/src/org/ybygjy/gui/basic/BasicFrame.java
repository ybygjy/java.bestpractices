package org.ybygjy.gui.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Frame测试
 * @author WangYanCheng
 * @version 2011-2-24
 */
public class BasicFrame {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                SideFrame sideFrame = new SideFrame();
            }
        });
    }
}

class SideFrame extends JFrame {
    public SideFrame() {
        setTitle("BasicFrame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        setSize(dimension);
        final GrphicsTest gt = new GrphicsTest();
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        getContentPane().add(gt);
        JButton btn = new JButton("SetStartFlag");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gt.setStartFlag(false);
            }
        });
        getContentPane().add(btn);
        setLocationByPlatform(true);
        setVisible(true);
    }
}

class GrphicsTest extends JComponent implements Runnable {
    /** 线程标记 */
    private boolean startFlag;
    private int x = 10;
    private int y = 10;

    public GrphicsTest() {
        startFlag = true;
        Thread th = new Thread(this);
        th.start();
    }

    public void run() {
        while (startFlag) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x += 5;
            y += 5;
            if (x > 800 || y > 800) {
                x = y = 10;
            }
            repaint();
        }
    }

    public void setStartFlag(boolean startFlag) {
        this.startFlag = startFlag;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        byte[] data = "HelloGraphics".getBytes();
        g.drawBytes(data, 0, data.length, x, y);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
