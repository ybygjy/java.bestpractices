package org.ybygjy.pattern.command.c03;

import java.awt.Button;

public abstract class Command extends Button {
    public Command(String caption) {
        super(caption);
    }
    public abstract void execute();
}
