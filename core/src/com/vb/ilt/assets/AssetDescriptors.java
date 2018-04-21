package com.vb.ilt.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class AssetDescriptors {

    private AssetDescriptors(){}

    public static final AssetDescriptor<BitmapFont> DEFAULT_FONT =
            new AssetDescriptor<>(AssetPaths.DEFAULT_FONT, BitmapFont.class);
    public static final AssetDescriptor<BitmapFont> STENCIL_FONT =
            new AssetDescriptor<>(AssetPaths.STENCIL_FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> HUD =
            new AssetDescriptor<>(AssetPaths.HUD, TextureAtlas.class);
    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<>(AssetPaths.SKIN, Skin.class);
    public static final AssetDescriptor<TextureAtlas> PLAYER =
            new AssetDescriptor<>(AssetPaths.PLAYER, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> NPC =
            new AssetDescriptor<>(AssetPaths.NPC, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> CLOSE_UP =
            new AssetDescriptor<>(AssetPaths.CLOSE_UP, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> PANELS =
            new AssetDescriptor<>(AssetPaths.DIALOGS, TextureAtlas.class);

    public static final AssetDescriptor<Sound> STEP_SOUND =
            new AssetDescriptor<>(AssetPaths.STEP_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> DOOR_SOUND =
            new AssetDescriptor<>(AssetPaths.DOOR_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> FINISHED_SOUND =
            new AssetDescriptor<>(AssetPaths.FINISHED_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> ACHIEVEMENT_SOUND =
            new AssetDescriptor<>(AssetPaths.ACHIEVEMENT_SOUND, Sound.class);
    public static final AssetDescriptor<Music> MAIN_MUSIC =
            new AssetDescriptor<>(AssetPaths.MAIN_MUSIC, Music.class);

    public static final AssetDescriptor<ParticleEffect> DIRT_PARTICLES =
            new AssetDescriptor<>(AssetPaths.DIRT_PARTICLES, ParticleEffect.class);
}