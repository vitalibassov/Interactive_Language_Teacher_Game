package com.vb.ilt.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ZOrderComponent implements Component, Pool.Poolable {

    public int z = -1;

    @Override
    public void reset() {
        z = -1;
    }
}
