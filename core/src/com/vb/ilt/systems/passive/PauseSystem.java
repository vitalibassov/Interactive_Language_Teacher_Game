package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.ui.stages.PauseCallback;
import com.vb.ilt.ui.stages.PauseStage;

public class PauseSystem extends EntitySystem{

    private final Stage pauseStage;

    public PauseSystem(AssetManager assetManager, Viewport hudViewport, SpriteBatch batch, PauseCallback pauseCallback) {
        this.pauseStage = new PauseStage(hudViewport, batch, assetManager, pauseCallback);
        this.setProcessing(false);
    }

    @Override
    public void update(float deltaTime) {
        pauseStage.act();
        pauseStage.draw();
        Gdx.input.setInputProcessor(pauseStage);
    }
}
