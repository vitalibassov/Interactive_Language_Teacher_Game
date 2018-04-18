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

    public static final String MAIN_CHARACTER_CLOSE_UP = "mainCharacter";

    public static final String MONOLOGUE_SPEECH = "monologue";
    public static final String PAUSE_PANEL = "pausePanel";
    public static final String ASSESSMENT_PANEL = "assessmentPanel";
    public static final String PAUSE_BACKGROUND = "pauseBackground";
    public static final String BACKGROUND = "background";

    public static final String COIN = "coin";
    public static final String STAR = "star";
    public static final String EMPTY_STAR = "empty-star";

    public static final String BAR_FRAME = "bar-frame";
    public static final String BAR = "bar";

    public static final String NPC_SHEPHERD = CharacterType.FROG.name().toLowerCase();
    public static final String NPC_BLACKSMITH = CharacterType.BEE.name().toLowerCase();
    public static final String NPC_MERCHANT = CharacterType.CAT.name().toLowerCase();

}
