package org.ybygjy.event;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 事件机制学习
 * @author WangYanCheng
 * @version 2011-1-21
 */
public class ButtonTest {
    /**
     * 入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ButtonFrame bfInst = new ButtonFrame();
                bfInst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                bfInst.setVisible(true);
            }
        });
    }
}

/**
 * ButtonFrame
 * @author WangYanCheng
 * @version 2011-1-21
 */
class ButtonFrame extends JFrame {
    /** MainPanel */
    private JPanel buttonPanel;

    /**
     * Constructor
     */
    public ButtonFrame() {
        setTitle("ButtonFrame");
        setSize(300, 200);
        JButton yellowBtn, redBtn, greenBtn;
        yellowBtn = new JButton("YelloButton");
        redBtn = new JButton("RedButton");
        greenBtn = new JButton("GreenButton");
        buttonPanel = new JPanel();
        buttonPanel.add(yellowBtn);
        buttonPanel.add(redBtn);
        buttonPanel.add(greenBtn);
        add(buttonPanel);
        yellowBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setBackground(Color.YELLOW);
            }
        });
        redBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setBackground(Color.RED);
            }
        });
        greenBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setBackground(Color.GREEN);
            }
        });
        JDialog dialog = new JDialog(this);
        dialog.setTitle("Test");
        greenBtn.addActionListener(EventHandler.create(ActionListener.class, dialog, "show"));
    }
}
