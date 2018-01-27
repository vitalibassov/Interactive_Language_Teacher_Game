package com.vb.ilt.entity.components.world;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.utils.Pool;

public class TiledMapRendererComponent implements Component, Pool.Poolable {

    public IsometricTiledMapRenderer mapRenderer;

    @Override
    public void reset() {
        mapRenderer = null;
    }
}
