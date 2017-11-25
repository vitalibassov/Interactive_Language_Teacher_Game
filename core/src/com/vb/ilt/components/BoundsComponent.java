package com.vb.ilt.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class BoundsComponent implements Component, Pool.Poolable{

    public Rectangle rectangle;

    @Override
    public void reset() {
        rectangle = null;
    }
}
