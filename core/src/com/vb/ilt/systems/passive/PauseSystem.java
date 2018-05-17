package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.ui.stages.PauseCallback;
import com.vb.ilt.ui.stages.PauseStage;

public class PauseSystem extends EntitySystem{

    private final Stage pauseStage;

    public PauseSystem(AssetManager assetManager, Viewport hudViewport, Batch batch, PauseCallback pauseCallback) {
        this.pauseStage = new PauseStage(hudViewport, batch, assetManager, pauseCallback);
        this.setProcessing(false);
    }

    @Override
    public void update(float deltaTime) {
        pauseStage.act();
        pauseStage.draw();
        Gdx.input.setInputProcessor(pauseStage);
    }

    public void smoothlyAppear(float duration){
        ((PauseStage)pauseStage).smoothlyAppear(duration);
    }

    public void smoothlyDisappear(float duration){
        ((PauseStage)pauseStage).smoothlyDisappear(duration);
    }
}
