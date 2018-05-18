package com.vb.ilt.screen.transition.transitions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.vb.ilt.screen.transition.ScreenTransitionBase;
import com.vb.ilt.util.GdxUtils;

public class FadeScreenTransition extends ScreenTransitionBase {

    public FadeScreenTransition(float duration) {
        super(duration);
    }

    @Override
    public void render(SpriteBatch batch, Texture currentScreenTexture, Texture nextScreenTexture, float percentage) {
        int currentScreenWidth = currentScreenTexture.getWidth();
        int currentScreenHeight = currentScreenTexture.getHeight();
        int nextScreenWidth = nextScreenTexture.getWidth();
        int nextScreenHeight = nextScreenTexture.getHeight();

        percentage = Interpolation.fade.apply(percentage);
        GdxUtils.clearScreen();

        Color oldColor = batch.getColor().cpy();

        batch.begin();

        batch.setColor(1, 1, 1, 1f - percentage);
        batch.draw(currentScreenTexture,
                0, 0,
                0, 0,
                currentScreenWidth, currentScreenHeight,
                1, 1,
                0,
                0, 0,
                currentScreenWidth, currentScreenHeight,
                false, true
        );

        batch.setColor(1, 1, 1, percentage);
        batch.draw(nextScreenTexture,
                0, 0,
                0, 0,
                nextScreenWidth, nextScreenHeight,
                1, 1,
                0,
                0, 0,
                nextScreenWidth, nextScreenHeight,
                false, true
        );

        batch.setColor(oldColor);
        batch.end();
    }
}
