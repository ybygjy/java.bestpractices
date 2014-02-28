package org.ybygjy.gui.game1;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Game1MainPanel extends JPanel {

    /**
     * serialize Number
     */
    private static final long serialVersionUID = 3077726301480754029L;
    /**窗体的宽*/
    private static final int WIDTH = 480;
    /**窗体的高*/
    private static final int HEIGHT = 480;
    /**背景方格的默认行数*/
    private static final int ROW = 15;
    /**背景方格的默认列数*/
    private static final int COL = 15;
    /**
     * 单个图像大小，需要和窗体大小比例协调
     * <p>屏幕宽度=单个图像大小*默认行数=32*15=480<p>
     * <p>屏幕高度=单个图像大小*默认列数=32*15=480</p>
     */
    private static final int CS = 32;
    /**
     * 设定地图
     * <p>通常在rpg类型游戏开发中，以二维数组为基础进行地图处理，用以描绘出X坐标和Y坐标</p>
     */
    private int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,1,1,1,1,1,1,1,1,0,0,0,1},
        {1,0,0,1,0,0,0,0,0,0,1,0,0,0,1},
        {1,0,0,1,0,0,0,0,2,0,1,0,0,0,1},
        {1,0,0,1,0,0,0,0,0,0,1,0,0,0,1},
        {1,0,0,1,1,1,0,0,1,1,1,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    /**背景图像*/
    private Image floorImage;
    /**城墙图像*/
    private Image wallImage;
    /**hero*/
    private Image heroImage;
    /**角色坐标X*/
    private volatile int hX;
    /**角色坐标Y*/
    private volatile int hY;
    /**左边移动*/
    private static final int LEFT = 0;
    /**右边移动*/
    private static final int RIGHT = 1;
    /**向上移动*/
    private static final int UP = 2;
    /**向下移动*/
    private static final int DOWN = 3;
    public Game1MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loadImage();
        setFocusable(true);
        addKeyListener(new InnerKeyListener());
        hX = 8;
        hY = 8;
    }
    private void loadImage() {
        ImageIcon floor = new ImageIcon(getClass().getResource("images/floor.gif"));
        ImageIcon wall = new ImageIcon(getClass().getResource("images/wall.gif"));
        ImageIcon hero = new ImageIcon(getClass().getResource("images/hero.gif"));
        this.floorImage = floor.getImage();
        this.wallImage = wall.getImage();
        this.heroImage = hero.getImage();
    }
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        drawMap(g);
        drawHero(g);
    }
    private void drawMap(Graphics g) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                switch(map[i][j]) {
                    case 0:
                        g.drawImage(floorImage,j * CS, i * CS, this);
                        break;
                    case 1:
                        g.drawImage(wallImage, j * CS, i * CS, this);
                        break;
                    case 2:
                        g.drawImage(floorImage, j * CS, i * CS, this);
                        break;
                    default :
                        break;
                }
            }
        }
    }
    private void drawHero(Graphics g) {
        g.drawImage(heroImage, hX * CS, hY * CS, this);
    }
    /**
     * 移动hero
     * @param flag 移动标记，标识是哪个方向移动
     */
    private void move(int flag) {
        switch (flag) {
            case LEFT:
                if (isAllow(hX-1, hY)) {
                    hX--;
                }
                break;
            case RIGHT:
                if (isAllow(hX+1, hY)) {
                    hX++;
                }
                break;
            case UP:
                if (isAllow(hX, hY-1)) {
                    hY--;
                }
                break;
            case DOWN:
                if (isAllow(hX, hY+1)) {
                    hY++;
                }
                break;
        }
        repaint();
    }
    private boolean isAllow(int x, int y) {
        if (map[y][x] != 1) {
            return true;
        }
        return false;
    }
    class InnerKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            int flag = 0;
            switch(keyCode) {
                case KeyEvent.VK_LEFT:
                    flag = LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    flag = RIGHT;
                    break;
                case KeyEvent.VK_UP:
                    flag = UP;
                    break;
                case KeyEvent.VK_DOWN:
                    flag = DOWN;
                    break;
            }
            Game1MainPanel.this.move(flag);
        }

        public void keyReleased(KeyEvent e) {
        }
    }
}
