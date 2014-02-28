package org.ybygjy.gui.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Java对几何图形的支持
 * @author WangYanCheng
 * @version 2011-2-24
 */
public class DrawTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new DrawFrame();
            }
        });
    }
}

class DrawFrame extends JFrame {
    public DrawFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("DrawTitle");
        setSize(new Dimension(800, 600));
        add(new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(Color.RED);
                double leftX = 100;
                double topY = 100;
                double width = 200;
                double height = 150;
                Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
                g2.draw(rect);
                Ellipse2D ellipse = new Ellipse2D.Double();
                ellipse.setFrame(rect);
                g2.draw(ellipse);
                g2.draw(new Line2D.Double(leftX, topY, leftX + width, topY + height));

                double centerX = rect.getCenterX();
                double centerY = rect.getCenterY();
                double radius = 150;
                Ellipse2D circle = new Ellipse2D.Double();
                circle.setFrameFromCenter(centerX, centerY, centerX + radius, centerY + radius);
                g2.draw(circle);
            }
        });
        setVisible(true);
    }
}
