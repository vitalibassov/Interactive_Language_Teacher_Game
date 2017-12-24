package com.vb.ilt.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public final class AssetDescriptors {

    private AssetDescriptors(){}

    public static final AssetDescriptor<TextureAtlas> HUD =
            new AssetDescriptor<TextureAtlas>(AssetPaths.HUD, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> PLAYER =
            new AssetDescriptor<TextureAtlas>(AssetPaths.PLAYER, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> NPC =
            new AssetDescriptor<TextureAtlas>(AssetPaths.NPC, TextureAtlas.class);
}
