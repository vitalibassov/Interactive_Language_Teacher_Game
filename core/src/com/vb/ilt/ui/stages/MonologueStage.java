package com.vb.ilt.ui.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;

public abstract class MonologueStage extends Stage {
    final Skin skin;
    Label text;
    ExitCallback exitCallback;
    final AssetManager assetManager;

    protected TextureRegion region;

    MonologueStage(Viewport viewport, Batch batch, AssetManager assetManager, ExitCallback exitCallback) {
        super(viewport, batch);
        this.assetManager = assetManager;
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.region = assetManager.get(AssetDescriptors.PANELS).findRegion(RegionNames.MONOLOGUE_SPEECH);
        this.exitCallback = exitCallback;
        init();
    }

    protected abstract void init();
    public abstract void updateText(String text);
}
