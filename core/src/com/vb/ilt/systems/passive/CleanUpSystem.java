package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.npc.NPCComponent;
import com.vb.ilt.entity.components.world.WorldObjectComponent;

public class CleanUpSystem extends EntitySystem{

    private static final Logger log = new Logger(CleanUpSystem.class.getName(), Logger.DEBUG);

    private static final Family NPC = Family.all(
            NPCComponent.class
    ).get();

    private static final Family WORLD_OBJECT = Family.all(
            WorldObjectComponent.class
    ).get();

    @Override
    public boolean checkProcessing() {
        return false;
    }

    public void cleanUp(){
        removeNPCs();
        removeWorldObjects();
    }

    private void removeNPCs(){
        removeObjects(NPC);
    }

    private void removeWorldObjects(){
        removeObjects(WORLD_OBJECT);
    }

    private void removeObjects(Family family){
        for (Entity entity : getEngine().getEntitiesFor(family)){
            log.debug("REMOVING ENTITY");
            getEngine().removeEntity(entity);
        }
    }
}
