package org.ybygjy.pattern.proxy.virtual.imageLoad;

import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.Icon;

/**
 * ImageIcon代理
 * @author WangYanCheng
 * @version 2011-1-13
 */
public class ImageProxy implements Icon {
    /** realObj */
    private ImageIcon imageIcon = null;
    /** image url */
    private URL imageURL;
    /** retrievalThread */
    private Thread retrievalThread;
    /** retrieval flag */
    boolean retrieving = false;

    /**
     * Constructor
     * @param imageURL imageURL
     */
    public ImageProxy(URL imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * isRetrieval
     * @return true/false
     */
    private boolean isRetrieval() {
        return null != imageIcon;
    }

    /**
     * {@inheritDoc}
     */
    public int getIconHeight() {
        return isRetrieval() ? imageIcon.getIconHeight() : 600;
    }

    /**
     * {@inheritDoc}
     */
    public int getIconWidth() {
        return isRetrieval() ? imageIcon.getIconWidth() : 800;
    }

    /**
     * {@inheritDoc}
     */
    public void paintIcon(final Component c, Graphics g, int x, int y) {
        if (isRetrieval()) {
            imageIcon.paintIcon(c, g, x, y);
        } else {
            g.drawString("正在加载，请稍候。。。。", x + 300, y + 300);
            if (!retrieving) {
                retrieving = true;
                retrievalThread = new Thread(new Runnable() {
                    /**
                     * {@inheritDoc}
                     */
                    public void run() {
                        try {
                            imageIcon = new ImageIcon(imageURL, "CD Cover.");
                            c.repaint();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                retrievalThread.start();
            }
        }
    }

}
