package com.vb.ilt.screen.transition.transitions;

import com.badlogic.gdx.math.Interpolation;
import com.vb.ilt.screen.transition.ScreenTransition;
import com.vb.ilt.util.Direction;

public final class ScreenTransitions {

    public static final ScreenTransition FADE = new FadeScreenTransition(1f);
    public static final ScreenTransition SCALE = new ScaleScreenTransition(1f, true, Interpolation.linear);
    public static final ScreenTransition SLIDE = new SlideScreenTransition(1.5f,true, Direction.DOWN, Interpolation.smooth2);
    public static final ScreenTransition SLIDE_TO_GAME_SCREEN = new SlideScreenTransition(1.5f,false, Direction.UP, Interpolation.smooth2);

    private ScreenTransitions(){}
}
