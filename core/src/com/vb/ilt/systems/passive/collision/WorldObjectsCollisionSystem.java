package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.world.WorldObjectComponent;

public class WorldObjectsCollisionSystem extends CollisionBase {

    private static final Logger log = new Logger(WorldObjectsCollisionSystem.class.getName(), Logger.DEBUG);

    private static final Family WORLD_OBJECT_FAMILY = Family.all(
            BoundsComponent.class,
            WorldObjectComponent.class
    ).get();

    public WorldObjectsCollisionSystem() {
        super(WORLD_OBJECT_FAMILY);
    }
}
