package com.vb.ilt.entity;

public enum NPCType {
    NONE,
    BLACKSMITH,
    MERCHANT,
    SHEPHERD;

    public boolean isNone(){return this == NONE;}
    public boolean isBlacksmith(){return this == BLACKSMITH;}
    public boolean isMerchant(){return this == MERCHANT;}
    public boolean isShepherd(){return this == SHEPHERD;}
}
