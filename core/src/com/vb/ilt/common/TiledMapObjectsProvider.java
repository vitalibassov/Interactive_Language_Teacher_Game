package com.vb.ilt.common;

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

import java.util.HashMap;
import java.util.Map;


public class TiledMapObjectsProvider {

    private static final Logger log = new Logger(TiledMapObjectsProvider.class.getName(), Logger.DEBUG);

    private final TiledMap map;

    public TiledMapObjectsProvider(String initialMap) {
        map = new TmxMapLoader().load(initialMap);
    }

    public TiledMap getMap(){
        return map;
    }

    public Array<PolygonMapObject> getCollisionObjects(){
        MapLayer objectsLayer = map.getLayers().get("Collision");
        MapObjects objects = objectsLayer.getObjects();
        return objects.getByType(PolygonMapObject.class);
    }

    public Vector2 getPlayerSpawnPoint(){
        MapLayer objectsLayer = map.getLayers().get("SpawnPoints");
        MapObjects objects = objectsLayer.getObjects();
        MapObject object = objects.get("SpawnPlayer");
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        return worldToIso(rect);
    }

    public Map<Vector2, String> getNpcSpawnPoints(){
        MapLayer objectsLayer = map.getLayers().get("NPCSpawnPoints");
        MapObjects objects = objectsLayer.getObjects();
        Map<Vector2, String> spawnPoints = new HashMap<Vector2, String>();
        for(MapObject object: objects){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            spawnPoints.put(worldToIso(rect), object.getName());
        }
        return spawnPoints;
    }

    public Map<Vector2, String> getSpawnsNearSensors(){
        MapLayer objectsLayer = map.getLayers().get("SensorSpawns");
        MapObjects objects = objectsLayer.getObjects();
        Map<Vector2, String> spawnPoints = new HashMap<Vector2, String>();
        for(MapObject object: objects){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            spawnPoints.put(worldToIso(rect), object.getName());
        }
        return spawnPoints;
    }

    public Map<PolygonMapObject, String> getSensors(){
        MapLayer objectsLayer = map.getLayers().get("Sensors");
        MapObjects objects = objectsLayer.getObjects();
        Map<PolygonMapObject, String> sensors = new HashMap<PolygonMapObject, String>();
        for(PolygonMapObject object: objects.getByType(PolygonMapObject.class)){
            sensors.put(object, object.getName());
        }
        return sensors;
    }

    private Vector2 worldToIso(Rectangle spawnRect) {
        float x = ((spawnRect.y + spawnRect.x) / (GameConfig.TILE_HEIGHT * GameConfig.MAP_SCALE_MULTIPLIER));
        float y = ((spawnRect.y - spawnRect.x) / (GameConfig.TILE_WIDTH * GameConfig.MAP_SCALE_MULTIPLIER) + GameConfig.DEFAULT_Y_OFFSET);
        return new Vector2(x, y);
    }
}