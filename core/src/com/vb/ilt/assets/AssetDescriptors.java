package com.vb.ilt.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class AssetDescriptors {

    private AssetDescriptors(){}

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> HUD =
            new AssetDescriptor<TextureAtlas>(AssetPaths.HUD, TextureAtlas.class);
    public static final AssetDescriptor<Skin> SKIN =
            new AssetDescriptor<Skin>(AssetPaths.SKIN, Skin.class);
    public static final AssetDescriptor<TextureAtlas> PLAYER =
            new AssetDescriptor<TextureAtlas>(AssetPaths.PLAYER, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> NPC =
            new AssetDescriptor<TextureAtlas>(AssetPaths.NPC, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> DIALOGS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.DIALOGS, TextureAtlas.class);
}
