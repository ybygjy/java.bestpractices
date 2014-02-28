package org.ybygjy.pattern.command.c02.impl;

import org.ybygjy.pattern.command.c02.AudioPlayer;
import org.ybygjy.pattern.command.c02.Command;

public class RewindCommand implements Command {
    private AudioPlayer audioPlayer;
    public RewindCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }
    public void execute() {
        this.audioPlayer.rewind();
    }
}
