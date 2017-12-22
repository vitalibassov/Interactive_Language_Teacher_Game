package com.vb.ilt.common;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.entity.NPCType;

import java.util.HashMap;
import java.util.Map;


public class TiledMapLayersProvider {

    private static final Logger log = new Logger(TiledMapLayersProvider.class.getName(), Logger.DEBUG);

    private final TiledMap map;
    private final OrthographicCamera camera;

    public TiledMapLayersProvider(String initialMap, OrthographicCamera camera) {
        map = new TmxMapLoader().load(initialMap);
        this.camera = camera;
    }

    public TiledMap getMap(){
        return map;
    }

    public Array<PolygonMapObject> getPolygons(){
        MapLayer objectsLayer = map.getLayers().get("Collision");
        MapObjects objects = objectsLayer.getObjects();
        log.debug("" + objects.getByType(RectangleMapObject.class).size);
        return objects.getByType(PolygonMapObject.class);
    }

    public Vector2 getPlayerSpawnPoint(){
        MapLayer objectsLayer = map.getLayers().get("SpawnPoints");
        MapObjects objects = objectsLayer.getObjects();
        MapObject object= objects.get("SpawnPlayer");
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        return worldToIso(rect);
    }

    public Map<Vector2, NPCType> getNpcSpawnPoints(){
        MapLayer objectsLayer = map.getLayers().get("NPCSpawnPoints");
        MapObjects objects = objectsLayer.getObjects();
        Map<Vector2, NPCType> spawnPoints = new HashMap<Vector2, NPCType>();
        for(MapObject object: objects){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            NPCType type = NPCType.valueOf(object.getName().toUpperCase());
            spawnPoints.put(worldToIso(rect), type);
        }
        return spawnPoints;
    }

    private Vector2 worldToIso(Rectangle spawnRect) {
        float x = ((spawnRect.y + spawnRect.x) / (GameConfig.TILE_HEIGHT * GameConfig.MAP_SCALE_MULTIPLIER));
        float y = ((spawnRect.y - spawnRect.x) / (GameConfig.TILE_WIDTH * GameConfig.MAP_SCALE_MULTIPLIER) + GameConfig.DEFAULT_Y_OFFSET);
        return new Vector2(x, y);
    }
}
