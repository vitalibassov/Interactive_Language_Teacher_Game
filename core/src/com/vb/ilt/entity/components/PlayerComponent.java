package com.vb.ilt.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable{

    public int score = 0;

    @Override
    public void reset() {
        score = 0;
    }
}
