package org.ybygjy.pattern.command.c02.impl;

import java.util.ArrayList;
import java.util.List;

import org.ybygjy.pattern.command.c02.Command;
import org.ybygjy.pattern.command.c02.MacroCommand;

public class MacroAudioCommand implements MacroCommand {
    private List<Command> commandList;
    public MacroAudioCommand() {
        commandList = new ArrayList<Command>();
    }
    public void execute() {
        System.out.println("Start executing MacroCommand!");
        for (Command command : commandList) {
            command.execute();
        }
        System.out.println("Stop executing MacroCommand!");
    }

    public void remove(Command command) {
        commandList.remove(command);
    }

    public void add(Command command) {
        commandList.add(command);
    }

}
