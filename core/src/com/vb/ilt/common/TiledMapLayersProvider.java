package com.vb.ilt.common;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.config.GameConfig;


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

    public Vector2 getSpawnPoint(){
        MapLayer objectsLayer = map.getLayers().get("SpawnPoints");
        MapObjects objects = objectsLayer.getObjects();
        MapObject object= objects.get("SpawnPlayer");
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        return new Vector2(rect.getX() / GameConfig.PIXELS_PER_CELL, rect.getY() / GameConfig.PIXELS_PER_CELL);
    }
}
