package com.vb.ilt.common;

public enum GameState {

    PLAYING,
    PAUSE,
    FINISH;

    public boolean isPlaying(){return this == PLAYING;}
    public boolean isPause(){return this == PAUSE;}
    public boolean isFinish(){return this == FINISH;}

}
