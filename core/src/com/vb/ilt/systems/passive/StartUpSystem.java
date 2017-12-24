package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.vb.ilt.common.TiledMapLayersProvider;

public class StartUpSystem extends EntitySystem{

    private EntityFactorySystem factory;
    private final OrthographicCamera camera;

    public StartUpSystem(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean checkProcessing() {
        return false;
    }

    @Override
    public void addedToEngine(Engine engine) {
        factory = engine.getSystem(EntityFactorySystem.class);
        startUp();
    }

    private void startUp(){
        //TiledMapLayersProvider provider = new TiledMapLayersProvider("maps/main_map.tmx");
        TiledMapLayersProvider provider = new TiledMapLayersProvider("maps/test isometric map/Testing_cart.tmx");
        factory.createMap(provider.getMap());
        factory.createNPCs(provider.getNpcSpawnPoints());
        factory.createPlayer(provider.getPlayerSpawnPoint());
        factory.createCollisionObjects(provider.getPolygons());
        factory.createControls();
    }
}
