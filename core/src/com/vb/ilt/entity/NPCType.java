package com.vb.ilt.entity;

public enum NPCType {

    BLACKSMITH,
    MERCHANT,
    SHEPHERD;

    public boolean isBlacksmith(){return this == BLACKSMITH;}
    public boolean isMerchant(){return this == MERCHANT;}
    public boolean isShepherd(){return this == SHEPHERD;}
}
