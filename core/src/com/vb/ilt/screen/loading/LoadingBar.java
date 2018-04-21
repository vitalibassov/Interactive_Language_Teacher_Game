package com.vb.ilt.screen.loading;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LoadingBar extends Actor {

    private TextureRegion barRegion;

    LoadingBar(TextureRegion barRegion) {
        this.barRegion = barRegion;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(barRegion, getX(), getY(), getWidth(), getHeight());
    }
}
