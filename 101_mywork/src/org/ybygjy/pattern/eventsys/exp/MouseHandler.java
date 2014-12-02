package org.ybygjy.pattern.eventsys.exp;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.beans.EventHandler;

import javax.swing.JFrame;

/**
 * MouseHandler
 * @author WangYanCheng
 * @version 2010-11-10
 */
public class MouseHandler extends JFrame {
    /**
     * Constructor
     */
    public MouseHandler() {
        super("MouseHandler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container contentPanel = getContentPane();
        contentPanel.addMouseListener(EventHandler.create(MouseListener.class, this, "pressed", "point",
            "mousePressed"));

        contentPanel.addMouseListener(EventHandler.create(MouseListener.class, this, "released", "point",
            "mouseReleased"));

    }

    /**
     * pressed
     * @param p {@link Point}
     */
    public void pressed(Point p) {
        System.out.println(p.getX() + ":" + p.getY());
    }

    /**
     * released
     * @param p {@link Point}
     */
    public void released(Point p) {
        System.out.println(p.getX() + ":" + p.getY());
    }

    /**
     * 测试入口
     * @param args arguments
     */
    public static void main(String[] args) {
        JFrame jframe =
        // new ButtonSelection();
        new MouseHandler();
        jframe.setSize(200, 100);
        jframe.setVisible(true);
    }
}
