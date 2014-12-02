package org.ybygjy.gui.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Swing字体
 * @author WangYanCheng
 * @version 2011-2-24
 */
public class FontTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        // "楷体_GB2312"
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FontFrame();
            }
        });
    }
}

class FontFrame extends JFrame {
    public FontFrame() {
        setSize(new Dimension(500, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(new FontComponent());
        setTitle("FontTest");
        setVisible(true);
    }

    public void doPaintFont(Graphics2D g2) {
        int x = 70;
        int y = 70;
        String[] fontes = getFontes();
        String message = "Hello世界！";
        for (String font : fontes) {
            g2.setFont(new Font(font, Font.BOLD, 14));
            g2.drawString(message, x, y);
            y += 20;
            if (y > 500) {
                x += 100;
                y = 70;
            }
        }
    }

    private String[] getFontes() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }
}

class FontComponent extends JComponent {
    String message = "Hello World good good good.";

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawRectFont(g2, new Font("Serif", Font.BOLD, 36));
    }

    private void drawRectFont(Graphics2D g2, Font font) {
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(message, context);
        double x = (getWidth() - bounds.getWidth()) / 2;
        double y = (getHeight() - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;

        g2.drawString(message, (float) x, (float) baseY);
        g2.setPaint(Color.RED);
        g2.draw(new Line2D.Double(x, baseY, x + bounds.getWidth(), baseY));
        g2.draw(new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight()));
    }
}
