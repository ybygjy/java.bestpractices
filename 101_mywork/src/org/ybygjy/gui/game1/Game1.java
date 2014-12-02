package org.ybygjy.gui.game1;

import javax.swing.JFrame;

public class Game1 extends JFrame {
    /**
     * serialize Number
     */
    private static final long serialVersionUID = -7308529441329488933L;
    public Game1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Java游戏中的地图");
        Game1MainPanel gmpInst = new Game1MainPanel();
        getContentPane().add(gmpInst);
        pack();
    }
    public static void main(String[] args) {
        Game1 gameInst = new Game1();
        gameInst.setVisible(true);
    }
}
