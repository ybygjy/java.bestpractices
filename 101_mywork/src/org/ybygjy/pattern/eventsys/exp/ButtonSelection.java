package org.ybygjy.pattern.eventsys.exp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 按钮选择小示例
 * @author WangYanCheng
 * @version 2010-11-10
 */
public class ButtonSelection extends JFrame {
    /**
     * Constructor
     */
    public ButtonSelection() {
        super("ButtonSelection");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JButton btn = new JButton("Pick Me");
        Container contentPane = getContentPane();
        contentPane.add(btn, BorderLayout.NORTH);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HelloWorld");
            }
        });
        btn.addActionListener(EventHandler.create(ActionListener.class, this, "print"));
        // btn.addMouseListener(EventHandler.create(MouseListener.class, this,
        // "print"));
        JTextField textField = new JTextField();
        JLabel label = new JLabel("text:");
        contentPane.add(label, BorderLayout.WEST);
        contentPane.add(textField, BorderLayout.CENTER);
        textField
            .addActionListener(EventHandler.create(ActionListener.class, label, "text", "source.text"));
    }

    /**
     * linked button listener
     */
    public void print() {
        System.out.println("HelloWorld for print Method.");
    }

    /**
     * 测试入口
     * @param args arguments
     */
    public static void main(String[] args) {
        JFrame jframe = new ButtonSelection();
        jframe.setSize(200, 100);
        jframe.setVisible(true);
    }
}
