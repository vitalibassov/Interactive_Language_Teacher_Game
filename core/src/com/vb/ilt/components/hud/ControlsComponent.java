package com.vb.ilt.components.hud;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;

public class ControlsComponent implements Component, Pool.Poolable{

    public Polygon topLeft;
    public Polygon topRight;
    public Polygon bottomLeft;
    public Polygon bottomRight;

    @Override
    public void reset() {
        topLeft = null;
        topRight = null;
        bottomLeft = null;
        bottomRight = null;
    }
}
