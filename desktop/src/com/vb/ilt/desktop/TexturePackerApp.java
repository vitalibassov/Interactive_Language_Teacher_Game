package com.vb.ilt.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerApp{

    private static final boolean DRAW_DEBUG_OUTLINE = false;

    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {

        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;
        settings.maxHeight = 2048;

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/hud",
                ASSETS_PATH + "/ui/hud",
                "hud"
        );

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/characters/player",
                ASSETS_PATH + "/characters/player",
                "player"
        );
    }
}
