package org.ybygjy.event;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * ActionTest
 * @author WangYanCheng
 * @version 2011-1-24
 */
public class ActionTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ActionFrame();
            }
        });
    }
}

class ActionFrame extends JFrame {
    /** JPanel */
    private JPanel mainPanel;

    /**
     * Constructor
     */
    public ActionFrame() {
        setTitle("ActionTest");
        setSize(800, 600);

        mainPanel = new JPanel();
        Action yellowAction = new ColorAction("Yellow", new ImageIcon("yellow.gif"), Color.YELLOW);
        Action orangeAction = new ColorAction("Orange", new ImageIcon("orange.gif"), Color.ORANGE);
        Action redAction = new ColorAction("Red", new ImageIcon("red.gif"), Color.RED);
        mainPanel.add(new JButton(yellowAction));
        mainPanel.add(new JButton(orangeAction));
        mainPanel.add(new JButton(redAction));
        add(mainPanel);

        InputMap inputMap = mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
        inputMap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.orange");
        inputMap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");

        ActionMap actionMap = mainPanel.getActionMap();
        actionMap.put("panel.yellow", yellowAction);
        actionMap.put("panel.orange", orangeAction);
        actionMap.put("panel.red", redAction);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    class ColorAction extends AbstractAction {
        /**
         * Constructor
         * @param name name
         * @param icon icon
         * @param c {@link Color}
         */
        public ColorAction(String name, Icon icon, Color c) {
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, icon);
            putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name.toLowerCase());
            putValue("color", c);
        }

        /**
         * {@inheritDoc}
         */
        public void actionPerformed(ActionEvent e) {
            Color c = (Color) getValue("color");
            mainPanel.setBackground(c);
        }
    }
}
