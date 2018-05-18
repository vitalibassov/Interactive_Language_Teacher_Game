package com.vb.ilt.screen.transition;

public abstract class ScreenTransitionBase implements ScreenTransition {

    protected final float duration;

    public ScreenTransitionBase(float duration){
        this.duration = duration;
    }

    @Override
    public float getDuration() {
        return duration;
    }
}
