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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.config.GameConfig;


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

    public Vector2 getSpawnPoint(){
        MapLayer objectsLayer = map.getLayers().get("SpawnPoints");
        MapObjects objects = objectsLayer.getObjects();
        MapObject object= objects.get("SpawnPlayer");
        Rectangle rect = ((RectangleMapObject) object).getRectangle();

        float x;
        float y;
        x = ((rect.y + rect.x) / GameConfig.TILE_HEIGHT);
        y = ((rect.y - rect.x) / GameConfig.TILE_WIDTH + 0.5f);
        //return worldToIso(new Vector3(rect.getX() / 64, rect.getY() / 64, 0.0f), 128, 64);
        return new Vector2(x, y);
    }

    private Vector2 worldToIso(Vector3 point, int tileWidth, int tileHeight) {
        camera.unproject(point);
        point.x /= tileWidth;
        point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
        point.x -= point.y - point.x;

        log.debug("SPAWN X= " + point.x + " SPAWN Y= " + point.y);
        return new Vector2(point.x, point.y);
    }
}
