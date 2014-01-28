package org.ybygjy.ui2;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 * UI界面总控
 * @author WangYanCheng
 * @version 2012-10-25
 */
public class MainFrameController {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jfInst = new MainUIFrame();
                jfInst.setSize(800, 600);
                jfInst.setVisible(true);
            }
        });
    }
}
/**
 * 封装界面构造
 * @author WangYanCheng
 * @version 2012-10-25
 */
class MainUIFrame extends JFrame {
    /** serial number */
    private static final long serialVersionUID = -305475266460168941L;
    private JPanel cntPanel;
    public MainUIFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cntPanel = new JPanel();
        cntPanel.setLayout(new BorderLayout());
        this.init();
        getContentPane().add(cntPanel);
    }
    private void init() {
        this.createNorth();
        this.createCenter();
        this.createSouth();
    }
    /**
     * 构建中央区域
     */
    private void createCenter() {
        JTabbedPane jtpInst = new JTabbedPane();
        jtpInst.addTab("01", createBtn("01"));
        jtpInst.addTab("02", createBtn("02"));
        jtpInst.addTab("03", createTab1());
        cntPanel.add(jtpInst, BorderLayout.CENTER);
    }
    private void createNorth() {
        JToolBar jtbInst = new JToolBar();
        jtbInst.add(createBtn("01"));
        jtbInst.add(createBtn("02"));
        jtbInst.add(createBtn("03"));
        cntPanel.add(jtbInst, BorderLayout.NORTH);
    }
    private void createSouth() {
        cntPanel.add(createBtn("SouthBtn"), BorderLayout.SOUTH);
    }
    private JButton createBtn(String btnName) {
        return new JButton(btnName);
    }
    private Component createTab1() {
        JTabbedPane innerJtpInst = new JTabbedPane(JTabbedPane.BOTTOM);
        innerJtpInst.addTab("InnerJtpInst_01", createSplitPane());
        innerJtpInst.addTab("InnerJtpInst_02", createBtn("InnerJtpInst_02"));
        return innerJtpInst;
    }
    private Component createSplitPane() {
        JSplitPane jspInst = new JSplitPane();
        jspInst.setOneTouchExpandable(true);
        jspInst.setDividerLocation(100);
        jspInst.setDividerSize(10);
        jspInst.add(createBtn("JSplitPane01"), JSplitPane.LEFT);
        jspInst.add(createBtn("JSplitPane02"), JSplitPane.RIGHT);
        return jspInst;
    }
}
