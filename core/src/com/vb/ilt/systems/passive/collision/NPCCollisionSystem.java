package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.npc.NPCComponent;

public class NPCCollisionSystem extends CollisionBase{
    private static final Logger log = new Logger(WorldWrapUpSystem.class.getName(), Logger.DEBUG);

    private static final Family NPC = Family.all(
            NPCComponent.class,
            BoundsComponent.class
    ).get();

    public NPCCollisionSystem() {
        super(NPC);
    }

}
