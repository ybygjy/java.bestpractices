package org.ybygjy.pattern.command.c03.impl;

import org.ybygjy.pattern.command.c03.Command;
import org.ybygjy.pattern.command.c03.UndoableTextArea;

public class UndoCommand extends Command {
    private UndoableTextArea utaInst;
    public UndoCommand(UndoableTextArea utaInst) {
        super("Undo");
        this.utaInst = utaInst;
    }
    public void execute() {
        this.utaInst.undo();
    }
}
