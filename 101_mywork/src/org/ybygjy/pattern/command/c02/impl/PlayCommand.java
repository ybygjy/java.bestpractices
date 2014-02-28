package org.ybygjy.pattern.command.c02.impl;

import org.ybygjy.pattern.command.c02.AudioPlayer;
import org.ybygjy.pattern.command.c02.Command;

public class PlayCommand implements Command {
    private AudioPlayer audioPlayer;

    public PlayCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }
    public void execute() {
        this.audioPlayer.play();
    }

}
