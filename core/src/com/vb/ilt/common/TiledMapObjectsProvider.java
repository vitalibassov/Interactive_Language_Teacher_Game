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
        return getMapObjects("Collision").getByType(PolygonMapObject.class);
    }

    public Vector2 getPlayerSpawnPoint(){
        MapObjects objects = getMapObjects("SpawnPoints");
        Rectangle rect = ((RectangleMapObject) objects.get("SpawnPlayer")).getRectangle();
        return worldToIso(rect);
    }

    public Map<Vector2, String> getNpcSpawnPoints(){
        return getSpawnPoints("NPCSpawnPoints");
    }

    public Map<Vector2, String> getSpawnsNearSensors(){
        return getSpawnPoints("SensorSpawns");
    }

    public Map<PolygonMapObject, String> getSensors(){
        Map<PolygonMapObject, String> sensors = new HashMap<PolygonMapObject, String>();
        for(PolygonMapObject object: getMapObjects("Sensors").getByType(PolygonMapObject.class)){
            sensors.put(object, object.getName());
        }
        return sensors;
    }
    // Layer must have objects with name and they must be rectangles
    private Map<Vector2, String> getSpawnPoints(String layerName){
        Map<Vector2, String> points = new HashMap<Vector2, String>();
        for (MapObject object : getMapObjects(layerName)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            points.put(worldToIso(rect), object.getName());
        }
        return points;
    }

    private MapObjects getMapObjects(String layerName){
        MapLayer objectsLayer = map.getLayers().get(layerName);
        return objectsLayer.getObjects();
    }

    private Vector2 worldToIso(Rectangle spawnRect) {
        float x = ((spawnRect.y + spawnRect.x) / (GameConfig.TILE_HEIGHT * GameConfig.MAP_SCALE_MULTIPLIER));
        float y = ((spawnRect.y - spawnRect.x) / (GameConfig.TILE_WIDTH * GameConfig.MAP_SCALE_MULTIPLIER) + GameConfig.DEFAULT_Y_OFFSET);
        return new Vector2(x, y);
    }
}
