package com.vb.ilt.systems.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Logger;

public class EntityLogger extends EntitySystem{

    private static final Logger log = new Logger(EntityLogger.class.getName(), Logger.DEBUG);

    private static final float TIMER = 2f;
    private float currentTime;

    @Override
    public void update(float deltaTime) {
        if(currentTime >= TIMER){
            log.debug("=====>>> CURRENT NUMBER OF ENTITIES: " + getEngine().getEntities().size());
            currentTime = 0;
        }
        currentTime += deltaTime;
    }
}
