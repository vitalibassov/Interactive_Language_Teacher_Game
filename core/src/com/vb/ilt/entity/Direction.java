package com.vb.ilt.entity;


public enum Direction {
    STANDING,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public boolean isStanding(){return this == STANDING;}
    public boolean isUp(){return this == UP;}
    public boolean isDown(){return this == DOWN;}
    public boolean isLeft(){return this == LEFT;}
    public boolean isRight(){return this == RIGHT;}
}
