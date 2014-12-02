package org.ybygjy.gui.basic;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Swing 图像组件
 * @author WangYanCheng
 * @version 2011-2-24
 */
public class ImageTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImageFrame();
            }
        });
    }
}

class ImageFrame extends JFrame {
    public ImageFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(new ImageComp());
        setSize(new Dimension(800, 600));
        setVisible(true);
    }
}

class ImageComp extends JComponent {
    private String imageUrl;
    private Image image;

    public ImageComp() {
        imageUrl = "http://apps.hi.baidu.com/ow/app/cover/18/10117.jpg";
        new Thread(new Runnable() {
            public void run() {
                try {
                    image = ImageIO.read(new URL(imageUrl));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }).start();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            int width = image.getWidth(this);
            int height = image.getHeight(this);
            int x = 0;
            int y = 0;
//            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            do {
                g.drawImage(image, x, y, width, height, null);
                x += width;
                if (x > getWidth()) {
                    x = 0;
                    y += height;
                }
                //System.out.println(x + ":" + y);
            } while (y <= getHeight());
        } else {
            g.drawString("Please waiting for a moment...", 10, 10);
        }
    }
}
