package com.vb.ilt.entity;

public enum CharacterType {
    NONE,
    AUTHOR,
    PROTAGONIST,
    BLACKSMITH,
    MERCHANT,
    SHEPHERD;

    public boolean isNone(){return this == NONE;}
    public boolean isAuthor() {return this == AUTHOR;}
    public boolean isProtagonist(){return this == PROTAGONIST;}
    public boolean isBlacksmith(){return this == BLACKSMITH;}
    public boolean isMerchant(){return this == MERCHANT;}
    public boolean isShepherd(){return this == SHEPHERD;}
}
