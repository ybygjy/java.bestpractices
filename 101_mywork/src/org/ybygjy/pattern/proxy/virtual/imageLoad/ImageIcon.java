package org.ybygjy.pattern.proxy.virtual.imageLoad;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;

/**
 * 负责耗时操作
 * @author WangYanCheng
 * @version 2011-1-13
 */
public class ImageIcon implements Icon {
    /** image */
    private BufferedImage image;

    /**
     * Constructor
     * @param imageURL imageURL
     * @param name imageName
     */
    public ImageIcon(URL imageURL, String name) {
        try {
            image = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getIconHeight() {
        return image.getHeight();
    }

    /**
     * {@inheritDoc}
     */
    public int getIconWidth() {
        return image.getWidth();
    }

    /**
     * {@inheritDoc}
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawImage(image, 0, 0, null);
    }
}
