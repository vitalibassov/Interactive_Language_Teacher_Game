package com.vb.ilt.entity.components.world;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Pool;


public class TiledMapComponent implements Component, Pool.Poolable{

    public TiledMap map;

    @Override
    public void reset() {
        map = null;
    }
}
