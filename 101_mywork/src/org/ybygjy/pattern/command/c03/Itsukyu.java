package org.ybygjy.pattern.command.c03;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

public class Itsukyu extends Frame {
    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;
    private static UndoableTextArea undoTextArea;
    private static ItsukyuQuotation panel;
    public Itsukyu(String title) {
        super(title);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        init();
        setSize(300, 400);
        setVisible(true);
    }
    private void init() {
        undoTextArea = new UndoableTextArea("Your text here.");
        panel = new ItsukyuQuotation(undoTextArea);
        add(panel);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Itsukyu("一休");
            }
        });
    }
}
