package com.vb.ilt.entity;

public enum CharacterType {
    NONE,
    AUTHOR,
    PROTAGONIST,
    BEE,
    CAT,
    FROG;

    public boolean isNone(){return this == NONE;}
    public boolean isAuthor() {return this == AUTHOR;}
    public boolean isProtagonist(){return this == PROTAGONIST;}
    public boolean isBee(){return this == BEE;}
    public boolean isCat(){return this == CAT;}
    public boolean isFrog(){return this == FROG;}
}
