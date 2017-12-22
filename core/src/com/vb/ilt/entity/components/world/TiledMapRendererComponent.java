package com.vb.ilt.entity.components.world;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

/**
 * Created by vitaa on 2017-11-24.
 */

public class TiledMapRendererComponent implements Component, com.badlogic.gdx.utils.Pool.Poolable{

    public IsometricTiledMapRenderer mapRenderer;

    @Override
    public void reset() {
        mapRenderer = null;
    }
}
