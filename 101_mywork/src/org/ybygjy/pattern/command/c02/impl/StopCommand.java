package org.ybygjy.pattern.command.c02.impl;

import org.ybygjy.pattern.command.c02.AudioPlayer;
import org.ybygjy.pattern.command.c02.Command;

public class StopCommand implements Command {
    private AudioPlayer audioPlayer;
    public StopCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }
    public void execute() {
        audioPlayer.stop();
    }

}
