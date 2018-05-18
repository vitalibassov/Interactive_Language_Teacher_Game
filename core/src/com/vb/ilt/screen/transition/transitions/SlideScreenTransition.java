package com.vb.ilt.screen.transition.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.vb.ilt.screen.transition.ScreenTransitionBase;
import com.vb.ilt.util.Direction;
import com.vb.ilt.util.GdxUtils;
import com.vb.ilt.util.Validate;


public class SlideScreenTransition extends ScreenTransitionBase {

    private boolean slideIn;
    private Direction direction;
    private Interpolation interpolation;

    public SlideScreenTransition(float duration){
        this(duration, false);
    }

    public SlideScreenTransition(float duration, boolean slideIn){
        this(duration, slideIn, Direction.LEFT);
    }

    public SlideScreenTransition(float duration, boolean slideIn, Direction direction) {
        this(duration, slideIn, direction, Interpolation.linear);
    }

    public SlideScreenTransition(float duration, boolean slideIn, Direction direction, Interpolation interpolation) {
        super(duration);

        Validate.notNull(direction, "direction is required.");
        Validate.notNull(interpolation, "interpolation is required");

        this.slideIn = slideIn;
        this.direction = direction;
        this.interpolation = interpolation;
    }

    @Override
    public void render(SpriteBatch batch, Texture currentScreenTexture, Texture nextScreenTexture, float percentage) {
        percentage = interpolation.apply(percentage);

        float x = 0;
        float y = 0;

        Texture bottomTexture = slideIn ? currentScreenTexture : nextScreenTexture;
        Texture topTexture = slideIn ? nextScreenTexture : currentScreenTexture;

        int bottomTextureWidth = bottomTexture.getWidth();
        int bottomTextureHeight = bottomTexture.getHeight();
        int topTextureWidth = topTexture.getWidth();
        int topTextureHeight = topTexture.getHeight();

        if(direction.isHorizontal()){
            float sign = direction.isLeft() ? -1 : 1;

            x = sign * topTextureWidth * percentage;
            if(slideIn){
                sign = -sign;
                x += sign * topTextureWidth;
            }
        }

        if(direction.isVertical()){
            float sign = direction.isUp() ? 1 : -1;

            y = sign * topTextureHeight * percentage;
            if(slideIn){
                sign = -sign;
                y += sign * topTextureHeight;
            }
        }

        GdxUtils.clearScreen();
        batch.begin();

        batch.draw(bottomTexture,
                0, 0,
                0, 0,
                bottomTextureWidth, bottomTextureHeight,
                1, 1,
                0,
                0, 0,
                topTextureWidth, topTextureHeight,
                false, true);

        batch.draw(topTexture,
                x, y,
                0, 0,
                topTextureWidth, topTextureHeight,
                1, 1,
                0,
                0, 0,
                topTextureWidth, topTextureHeight,
                false, true
        );

        batch.end();
    }
}
