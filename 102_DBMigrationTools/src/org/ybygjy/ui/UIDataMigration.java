package org.ybygjy.ui;

import javax.swing.SwingUtilities;

/**
 * 数据迁移程序图形界面入口
 * @author WangYanCheng
 * @version 2012-4-13
 */
public class UIDataMigration {

    /**
     * 执行入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                UIMainJFrame uimjInst = new UIMainJFrame();
                uimjInst.setVisible(true);
            }
        });
    }
}
