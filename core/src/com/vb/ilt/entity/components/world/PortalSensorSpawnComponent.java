package com.vb.ilt.entity.components.world;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;


public class PortalSensorSpawnComponent implements Component, Pool.Poolable{

    public String name;


    @Override
    public void reset() {
        name = null;
    }
}
