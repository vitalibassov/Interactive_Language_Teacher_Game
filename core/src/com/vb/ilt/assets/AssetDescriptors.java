package com.vb.ilt.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class AssetDescriptors {

    private AssetDescriptors(){}

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> HUD =
            new AssetDescriptor<TextureAtlas>(AssetPaths.HUD, TextureAtlas.class);
    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>(AssetPaths.SKIN, Skin.class);
    public static final AssetDescriptor<TextureAtlas> PLAYER =
            new AssetDescriptor<TextureAtlas>(AssetPaths.PLAYER, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> NPC =
            new AssetDescriptor<TextureAtlas>(AssetPaths.NPC, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> PANELS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.DIALOGS, TextureAtlas.class);
    public static final AssetDescriptor<Sound> STEP_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.STEP_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> DOOR_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.DOOR_SOUND, Sound.class);
    public static final AssetDescriptor<Music> MAIN_MUSIC =
            new AssetDescriptor<Music>(AssetPaths.MAIN_MUSIC, Music.class);
}