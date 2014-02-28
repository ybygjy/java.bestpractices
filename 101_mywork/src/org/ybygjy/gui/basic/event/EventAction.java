package org.ybygjy.gui.basic.event;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <p>
 * 1、注意Action接口与事件源可以是多对多
 * <p>
 * 1.1、一个事件源对应多个Action
 * <p>
 * 1.2、一个Action对应多个事件源
 * @author WangYanCheng
 * @version 2011-2-25
 */
public class EventAction {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ButtonFrame();
            }
        });
    }
}

class ButtonFrame extends JFrame {
    private JPanel buttonPanel;

    public ButtonFrame() {
        setTitle("ActionTest");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buttonPanel = new JPanel();
        getContentPane().add(buttonPanel);

        Action yellowAction = new ColorAction("yellow", new ImageIcon(""), Color.YELLOW);

        Action redAction = new ColorAction("red", new ImageIcon(""), Color.RED);
        Action blueAction = new ColorAction("blue", new ImageIcon(""), Color.BLUE);
        JButton tmpBtn = new JButton(yellowAction);
        tmpBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HelloWorld.");
            }
        });
        buttonPanel.add(tmpBtn);
        buttonPanel.add(new JButton(redAction));
        buttonPanel.add(new JButton(blueAction));
        setVisible(true);
    }

    class ColorAction extends AbstractAction {
        /**
         * serialized number
         */
        private static final long serialVersionUID = 1L;

        public ColorAction(String name, Icon icon, Color c) {
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, icon);
            putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name);
            putValue("color", c);
        }

        public void actionPerformed(ActionEvent e) {
            Color c = (Color) getValue("color");
            buttonPanel.setBackground(c);
        }
    }
}
