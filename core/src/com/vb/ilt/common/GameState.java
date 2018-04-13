package com.vb.ilt.common;

public enum GameState {

    PLAYING,
    PAUSE,
    QUIT,
    FINISH;

    public boolean isPlaying(){return this == PLAYING;}
    public boolean isPause(){return this == PAUSE;}
    public boolean isFinish(){return this == FINISH;}
    public boolean isQuit(){return this == QUIT;}

}
