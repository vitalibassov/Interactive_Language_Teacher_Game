package com.vb.ilt.config;

public final class GameConfig {

    public static final float WIDTH = 1920f;
    public static final float HEIGHT = 1080f;

    public static final float WORLD_SCALE = 80f;

    public static final float WORLD_WIDTH = WIDTH / WORLD_SCALE;
    public static final float WORLD_HEIGHT = HEIGHT / WORLD_SCALE;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f;

    public static final float HUD_WIDTH = 1920f;
    public static final float HUD_HEIGHT = 1080f;

    public static final float PLAYER_WIDTH = 1f;
    public static final float PLAYER_HEIGHT = 2f;
    public static final float PLAYER_VELOCITY = 0.15f;
    public static final float STOPPING_SPEED = 0.75f;

    private GameConfig(){}
}
