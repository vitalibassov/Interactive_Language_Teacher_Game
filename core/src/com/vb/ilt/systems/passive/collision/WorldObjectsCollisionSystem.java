package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Family;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.world.WorldCollisionObjectComponent;
import com.vb.ilt.entity.components.world.WorldObjectComponent;

public class WorldObjectsCollisionSystem extends CollisionBase {

    private static final Family WORLD_OBJECT_FAMILY = Family.all(
            BoundsComponent.class,
            WorldObjectComponent.class,
            WorldCollisionObjectComponent.class
    ).get();

    public WorldObjectsCollisionSystem() {
        super(WORLD_OBJECT_FAMILY);
    }
}
