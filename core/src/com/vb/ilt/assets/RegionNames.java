package com.vb.ilt.assets;

import com.vb.ilt.entity.CharacterType;

public final class RegionNames {

    private RegionNames(){}

    public static final String CONTROLS = "controls";

    public static final String PLAYER_FRONT = "playerFront";
    public static final String PLAYER_LEFT = "playerLeft";
    public static final String PLAYER_RIGHT = "playerRight";
    public static final String PLAYER_UP = "playerUp";
    public static final String PLAYER_DOWN = "playerDown";

    public static final String NPC_SHEPHERD = CharacterType.SHEPHERD.name().toLowerCase();
    public static final String NPC_BLACKSMITH = CharacterType.BLACKSMITH.name().toLowerCase();
    public static final String NPC_MERCHANT = CharacterType.MERCHANT.name().toLowerCase();

}
