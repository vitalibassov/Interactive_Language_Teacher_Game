package com.vb.ilt.common;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;


public class TiledMapLayersProvider {

    private static final Logger log = new Logger(TiledMapLayersProvider.class.getName(), Logger.DEBUG);

    private final TiledMap map;

    public TiledMapLayersProvider(String initialMap) {
        map = new TmxMapLoader().load(initialMap);
    }

    public TiledMap getMap(){
        return map;
    }

    public Array<RectangleMapObject> getPolygons(){
        MapLayer objectsLayer = map.getLayers().get("Collision");
        MapObjects objects = objectsLayer.getObjects();
        log.debug("" + objects.getByType(RectangleMapObject.class).size);
        return objects.getByType(RectangleMapObject.class);
    }
}
