package org.ybygjy.pattern.command.c01;

public class ConcreateCommand implements Command {
    private Receiver receiver;
    public ConcreateCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        this.receiver.action();
    }
}
