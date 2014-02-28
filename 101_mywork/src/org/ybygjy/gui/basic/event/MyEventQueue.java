package org.ybygjy.gui.basic.event;

import java.awt.AWTEvent;
import java.awt.EventQueue;

import javax.swing.JButton;

public class MyEventQueue extends EventQueue {
    public void dispatchEvent(AWTEvent ae) {
        if (ae.getSource() instanceof JButton) {
            System.out.println(((JButton) ae.getSource()).getText());
            super.dispatchEvent(ae);
        }
    }
}
