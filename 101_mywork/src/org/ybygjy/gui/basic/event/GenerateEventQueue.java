package org.ybygjy.gui.basic.event;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * <h2>利用Java事件处理机制实现录制、回放功能</h2> <h3><a
 * href="http://www.ibm.com/developerworks/cn/java/j-java-test/">链接</a></h3>
 * @author WangYanCheng
 * @version 2011-2-25
 */
public class GenerateEventQueue {
    public static void main(String[] args) {
        new EventQueueFrame();
        // Toolkit.getDefaultToolkit().getSystemEventQueue().push(new
        // MyEventQueue());
    }
}

class EventQueueFrame extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton btn;

    public EventQueueFrame() {
        doInit();
    }

    private void doInit() {
        btn = new JButton("abc");
        textField = new JTextField();
        //btn.addActionListener(this);
        this.getContentPane().add(textField, BorderLayout.CENTER);
        this.getContentPane().add(btn, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(100, 100));
        setVisible(true);
        EventQueue eq = getToolkit().getSystemEventQueue();
        eq.postEvent(new ActionEvent(btn, ActionEvent.ACTION_FIRST, "abcdef"));
    }

    public void actionPerformed(ActionEvent e) {
        textField.setText("event is:" + e.getActionCommand());
    }
}
