package com.vb.ilt.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;

public class TiledMapManager {

    private Map<String, TiledMapObjectsProvider> tiledMapObjects;
    private String currentMap;

    public TiledMapManager(String levelName){
        tiledMapObjects = new HashMap<>();
        for (FileHandle file : Gdx.files.internal(levelName).list()){
            if (file.extension().equals("tmx"))
            tiledMapObjects.put(file.nameWithoutExtension(), new TiledMapObjectsProvider(file.toString()));
            System.out.println(file.nameWithoutExtension());
        }
    }

    public void setMap(String mapName){
        this.currentMap = mapName;
    }

    public TiledMapObjectsProvider getMapProvider(String name){
        TiledMapObjectsProvider tiledMapObjectsProvider = tiledMapObjects.get(name);
        setMap(name);
        return tiledMapObjectsProvider;

    }

    public String getCurrentMap() {
        return currentMap;
    }
}
