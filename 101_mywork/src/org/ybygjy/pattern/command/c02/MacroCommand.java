package org.ybygjy.pattern.command.c02;

public interface MacroCommand extends Command {
    public void remove(Command command);
    public void add(Command command);
}
