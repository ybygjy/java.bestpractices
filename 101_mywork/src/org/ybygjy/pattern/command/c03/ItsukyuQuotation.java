package org.ybygjy.pattern.command.c03;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.ybygjy.pattern.command.c03.impl.RedoCommand;
import org.ybygjy.pattern.command.c03.impl.UndoCommand;

/**
 * Invoker
 * @author WangYanCheng
 * @version 2012-12-12
 */
public class ItsukyuQuotation extends Panel implements ActionListener {
    private Command undoCommand;
    private Command redoCommand;
    public ItsukyuQuotation(UndoableTextArea undoTextArea) {
        setLayout(new BorderLayout(5, 5));
        Panel toolBar = new Panel();
        undoCommand = new UndoCommand(undoTextArea);
        redoCommand = new RedoCommand(undoTextArea);
        undoCommand.addActionListener(this);
        redoCommand.addActionListener(this);
        toolBar.add(undoCommand);
        toolBar.add(redoCommand);
        add(toolBar, BorderLayout.NORTH);
        add(undoTextArea, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        Command cmd = (Command) e.getSource();
        cmd.execute();
    }
}
