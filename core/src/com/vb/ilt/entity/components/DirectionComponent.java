package com.vb.ilt.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.vb.ilt.entity.Direction;

public class DirectionComponent implements Component, Pool.Poolable{

    public Direction direction;
    @Override
    public void reset() {
        direction = null;
    }
}
