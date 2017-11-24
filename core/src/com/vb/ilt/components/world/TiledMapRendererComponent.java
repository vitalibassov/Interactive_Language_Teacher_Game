package com.vb.ilt.components.world;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by vitaa on 2017-11-24.
 */

public class TiledMapRendererComponent implements Component, com.badlogic.gdx.utils.Pool.Poolable{

    public OrthogonalTiledMapRenderer mapRenderer;

    @Override
    public void reset() {
        mapRenderer = null;
    }
}
