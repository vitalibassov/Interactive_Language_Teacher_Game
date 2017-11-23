package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

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
        factory.createPlayer();
    }
}
