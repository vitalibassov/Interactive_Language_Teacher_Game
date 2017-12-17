package com.vb.ilt.systems.active.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.components.BoundsComponent;
import com.vb.ilt.components.PlayerComponent;
import com.vb.ilt.components.world.WorldObjectComponent;
import com.vb.ilt.util.Mappers;

public class WorldObjectsCollisionSystem extends EntitySystem {

    private static final Logger log = new Logger(WorldObjectsCollisionSystem.class.getName(), Logger.DEBUG);

    private static final Family PLAYER_FAMILY = Family.all(
            BoundsComponent.class,
            PlayerComponent.class
    ).get();

    private static final Family WORLD_OBJECT_FAMILY = Family.all(
            BoundsComponent.class,
            WorldObjectComponent.class
    ).get();

    @Override
    public boolean checkProcessing() {
        return false;
    }

    public boolean checkCollision(Vector2 velocity) {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER_FAMILY);
        ImmutableArray<Entity> worldObjects = getEngine().getEntitiesFor(WORLD_OBJECT_FAMILY);

        for (Entity player : players) {
            BoundsComponent playerBounds = Mappers.BOUNDS.get(player);
            Polygon tempBounds = new Polygon();
            tempBounds.setVertices(playerBounds.polygon.getVertices());
            tempBounds.setPosition(
                    playerBounds.polygon.getX() + velocity.x,
                    playerBounds.polygon.getY() + velocity.y);
            for (Entity worldObject : worldObjects) {
                BoundsComponent worldObjectBounds = Mappers.BOUNDS.get(worldObject);
                if (Intersector.overlapConvexPolygons(tempBounds, worldObjectBounds.polygon)) {
                    log.debug("COLLISION WITH WORLD OBJECT IS HAPPENED");
                    return true;
                }
            }
        }
        return false;
    }
}
