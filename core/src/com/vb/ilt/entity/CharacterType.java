package com.vb.ilt.entity;

public enum CharacterType {
    NONE,
    PLAYER,
    BLACKSMITH,
    MERCHANT,
    SHEPHERD;

    public boolean isNone(){return this == NONE;}
    public boolean isPlayer() {return this == PLAYER;}
    public boolean isBlacksmith(){return this == BLACKSMITH;}
    public boolean isMerchant(){return this == MERCHANT;}
    public boolean isShepherd(){return this == SHEPHERD;}
}
