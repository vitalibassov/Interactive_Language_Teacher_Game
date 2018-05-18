package com.vb.ilt.screen.transition.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.vb.ilt.screen.transition.ScreenTransitionBase;
import com.vb.ilt.util.GdxUtils;
import com.vb.ilt.util.Validate;

public class ScaleScreenTransition extends ScreenTransitionBase {

    private boolean scaleOut;
    private Interpolation interpolation;

    public ScaleScreenTransition(float duration){
        this(duration, false);
    }

    public ScaleScreenTransition(float duration, boolean scaleOut){
        this(duration, scaleOut, Interpolation.linear);
    }

    public ScaleScreenTransition(float duration, boolean scaleOut, Interpolation interpolation) {
        super(duration);

        Validate.notNull(interpolation, "interpolation is required");

        this.scaleOut = scaleOut;
        this.interpolation = interpolation;
    }

    @Override
    public void render(SpriteBatch batch, Texture currentScreenTexture, Texture nextScreenTexture, float percentage) {
        percentage = interpolation.apply(percentage);

        float scale = 1 - percentage;

        if(scaleOut){
            scale = percentage;
        }

        Texture topTexture = scaleOut ? nextScreenTexture : currentScreenTexture;
        Texture bottomTexture = scaleOut ? currentScreenTexture : nextScreenTexture;

        int topTextureWidth = topTexture.getWidth();
        int topTextureHeight = topTexture.getHeight();
        int bottomTextureWidth = bottomTexture.getWidth();
        int bottomTextureHeight = bottomTexture.getHeight();

        GdxUtils.clearScreen();
        batch.begin();

        batch.draw(bottomTexture,
                0, 0,
                0, 0,
                bottomTextureWidth, bottomTextureHeight,
                1, 1,
                0,
                0, 0,
                bottomTextureWidth, bottomTextureHeight,
                false, true
        );

        batch.draw(topTexture,
                0, 0,
                topTextureWidth / 2f, topTextureHeight / 2f,
                topTextureWidth, topTextureHeight,
                scale, scale,
                0,
                0, 0,
                topTextureWidth, topTextureHeight,
                false, true);

        batch.end();
    }
}
