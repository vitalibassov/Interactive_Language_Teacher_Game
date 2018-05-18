package com.vb.ilt.screen.transition;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenTransition {

    float getDuration();
    void render(SpriteBatch batch, Texture currentScreenTexture,
                Texture nextScreenTexture, float percentage);
}
