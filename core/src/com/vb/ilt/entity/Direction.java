package com.vb.ilt.entity;

public enum Direction {
    IDLE(0),
    UP(1),
    DOWN(2),
    LEFT(3),
    RIGHT(4);

    private int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isIdle(){return this == IDLE;}
    public boolean isUp(){return this == UP;}
    public boolean isDown(){return this == DOWN;}
    public boolean isLeft(){return this == LEFT;}
    public boolean isRight(){return this == RIGHT;}
}
