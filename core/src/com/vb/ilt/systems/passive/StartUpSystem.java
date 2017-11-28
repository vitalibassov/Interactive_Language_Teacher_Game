package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.vb.ilt.common.TiledMapLayersProvider;

public class StartUpSystem extends EntitySystem{

    private EntityFactorySystem factory;

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
        TiledMapLayersProvider provider = new TiledMapLayersProvider("maps/main_map.tmx");
        factory.createMap(provider);
        factory.createPlayer(provider.getSpawnPoint());
        factory.createCollisionObjects(provider);

    }
}
