package org.ybygjy.pattern.command.c02;

public class Keypad {
    private Command stopCommand;
    private Command rewindCommand;
    private Command playCommand;
    public Keypad(Command stopCommand, Command rewindCommand, Command playCommand) {
        this.stopCommand = stopCommand;
        this.rewindCommand = rewindCommand;
        this.playCommand = playCommand;
    }
    public void play() {
        this.stopCommand.execute();
        this.playCommand.execute();
    }
    public void rewind() {
        this.rewindCommand.execute();
    }
    public void stop() {
        this.stopCommand.execute();
    }
}
