package com.vb.ilt.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent implements Component, Pool.Poolable{

    public ImmutableArray<Animation<TextureRegion>> animations;
    private int animationIndex;
    public float animationTime;

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void setAnimationIndex(int animationIndex) {
        if(animationIndex >= animations.size()){
            throw new IllegalArgumentException("Animation Index is greater or equal to the Animations size");
        }
        this.animationIndex = animationIndex;
    }

    @Override
    public void reset() {
        this.animations = null;
        this.animationIndex = -1;
        this.animationTime = 0;
    }
}
