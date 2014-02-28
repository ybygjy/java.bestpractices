package org.ybygjy.pattern.command.c03.impl;

import org.ybygjy.pattern.command.c03.Command;
import org.ybygjy.pattern.command.c03.UndoableTextArea;

public class RedoCommand extends Command {
    private UndoableTextArea utaInst;
    public RedoCommand(UndoableTextArea utaInst) {
        super("Redo");
        this.utaInst = utaInst;
    }
    public void execute() {
        this.utaInst.redo();
    }
}
