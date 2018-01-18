package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.PlayerComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.util.Mappers;

public abstract class CollisionBase extends EntitySystem{

    private final Family COLLISION_OBJECTS;

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            PositionComponent.class,
            BoundsComponent.class
    ).get();

    public CollisionBase(Family FAMILY) {
        this.COLLISION_OBJECTS = FAMILY;
    }

    public boolean checkCollision(Vector2 velocity){
        ImmutableArray<Entity> objects = getEngine().getEntitiesFor(COLLISION_OBJECTS);
        Entity player = getEngine().getEntitiesFor(PLAYER).get(0);

        PositionComponent playerPos = Mappers.POSITION.get(player);
        BoundsComponent playerBounds = Mappers.BOUNDS.get(player);

        Polygon tempPolygon = new Polygon(playerBounds.polygon.getVertices());
        tempPolygon.setPosition(playerPos.x + velocity.x, playerPos.y + velocity.y);

        for(Entity object : objects){
            BoundsComponent objectBounds = Mappers.BOUNDS.get(object);
            if (contains(tempPolygon.getTransformedVertices(), objectBounds.polygon)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkProcessing() {
        return false;
    }

    protected boolean contains(float[] vertices, Polygon polygon) {
        for (int i = 0, j = 1; j < vertices.length; i += 2, j += 2) {
            if (polygon.contains(vertices[i], vertices[j])) {
                return true;
            }
        }
        return false;
    }
}
