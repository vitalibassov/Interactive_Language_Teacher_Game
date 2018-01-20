package com.vb.ilt.entity;


public enum Direction {
    IDLE,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public boolean isIdle(){return this == IDLE;}
    public boolean isUp(){return this == UP;}
    public boolean isDown(){return this == DOWN;}
    public boolean isLeft(){return this == LEFT;}
    public boolean isRight(){return this == RIGHT;}
}
