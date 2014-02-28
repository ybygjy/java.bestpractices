package org.ybygjy.event;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 事件机制学习-鼠标事件
 * @author WangYanCheng
 * @version 2011-1-24
 */
public class MouseTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MouseFrame();
            }
        });
    }
}

class MouseFrame extends JFrame {
    private JPanel mainPanel;

    /**
     * Constructor
     */
    public MouseFrame() {
        setTitle("MouseTest");
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        MouseComponent mouseComp = new MouseComponent();
        add(mouseComp);
    }

    class MouseComponent extends JComponent {
        private List<Rectangle2D> squares;
        private Rectangle2D current;

        public MouseComponent() {
            squares = new ArrayList<Rectangle2D>();
            current = null;
            addMouseListener(new MouseAdapter() {

                /*
                 * (non-Javadoc)
                 * @seejava.awt.event.MouseAdapter#mouseClicked(java.awt.event.
                 * MouseEvent)
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    current = find(e.getPoint());
                    if (current != null && e.getClickCount() >= 2) {
                        remove(current);
                    }
                }

                /*
                 * (non-Javadoc)
                 * @seejava.awt.event.MouseAdapter#mousePressed(java.awt.event.
                 * MouseEvent)
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    current = find(e.getPoint());
                    if (current == null) {
                        add(e.getPoint());
                    }
                }
            });
            addMouseMotionListener(new MouseMotionListener() {

                public void mouseMoved(MouseEvent e) {
                    if (find(e.getPoint()) == null) {
                        setCursor(Cursor.getDefaultCursor());
                    } else {
                        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    }
                }

                public void mouseDragged(MouseEvent e) {
                    if (current != null) {
                        int x = e.getX();
                        int y = e.getY();
                        current.setFrame(x, y, 20, 20);
                        repaint();
                    }
                }
            });
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            for (Rectangle2D r : squares) {
                g2.draw(r);
            }
        }

        public Rectangle2D find(Point2D p) {
            for (Rectangle2D r : squares) {
                if (r.contains(p)) {
                    return r;
                }
            }
            return null;
        }

        public void add(Point2D p) {
            double x = p.getX();
            double y = p.getY();
            current = new Rectangle2D.Double(x, y, 20, 20);
            squares.add(current);
            repaint();
        }

        public void remove(Rectangle2D s) {
            if (s == null) {
                return;
            }
            if (s == current) {
                current = null;
            }
            squares.remove(s);
            repaint();
        }
    }
}
