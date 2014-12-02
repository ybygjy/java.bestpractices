package org.ybygjy.event;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 事件学习
 * @author WangYanCheng
 * @version 2011-1-21
 */
public class LookAndFeel {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                PlafFrame plafFrame = new PlafFrame();
                plafFrame.setVisible(true);
            }
        });
    }
}
class PlafFrame extends JFrame {
    private JPanel panel;
    public PlafFrame() {
        setTitle("PlafTest");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : infos) {
            makeBtn(info.getName(), info.getClassName());
        }
        add(panel);
    }
    private void makeBtn(String btnName, final String plafName) {
        JButton btn = new JButton(btnName);
        panel.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel(plafName);
                    SwingUtilities.updateComponentTreeUI(PlafFrame.this);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
