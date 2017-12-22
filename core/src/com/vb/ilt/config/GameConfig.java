package com.vb.ilt.config;

public final class GameConfig {

    public static final float WIDTH = 1920f;
    public static final float HEIGHT = 1080f;

    public static final float WORLD_SCALE = 80f;

    public static final float PIXELS_PER_CELL = 64f;
    public static final float MAP_SCALE = PIXELS_PER_CELL;

    public static final float TILE_WIDTH = MAP_SCALE * 2f;
    public static final float TILE_HEIGHT = MAP_SCALE;

    public static final float WORLD_WIDTH = WIDTH / WORLD_SCALE;
    public static final float WORLD_HEIGHT = HEIGHT / WORLD_SCALE;

    public static final float HUD_WIDTH = 1920f;
    public static final float HUD_HEIGHT = 1080f;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f;

    public static final float PLAYER_WIDTH = 1f;
    public static final float PLAYER_HEIGHT = 2f;
    public static final float PLAYER_VELOCITY = 0.15f;
    public static final float STOPPING_SPEED = 0.75f;
    public static final float PLAYER_HALF_WIDTH = PLAYER_WIDTH / 2f;
    public static final float PLAYER_HALF_HEIGHT = PLAYER_HEIGHT /2f;

    public static final float CONTROLS_SIZE = 300f;
    public static final float CONTROLS_X = 40f;
    public static final float CONTROLS_Y = 40f;

    private GameConfig(){}
}
