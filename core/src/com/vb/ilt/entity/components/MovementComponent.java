package com.vb.ilt.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class MovementComponent implements Component, Pool.Poolable{

    public Vector2 velocity = new Vector2();

    @Override
    public void reset() {
        velocity.setZero();
    }
}
