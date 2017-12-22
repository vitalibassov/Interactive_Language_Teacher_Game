package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.PlayerComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.world.TiledMapComponent;
import com.vb.ilt.util.Mappers;

public class WorldWrapUpSystem extends EntitySystem {

    private static final Logger log = new Logger(WorldWrapUpSystem.class.getName(), Logger.DEBUG);

    private static final Family WORLD = Family.all(
            TiledMapComponent.class,
            BoundsComponent.class
    ).get();

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            PositionComponent.class,
            BoundsComponent.class
    ).get();

    @Override
    public boolean checkProcessing() {
        return false;
    }

    public boolean outOfWorld(Vector2 velocity) {
        Entity map = getEngine().getEntitiesFor(WORLD).get(0);
        Entity player = getEngine().getEntitiesFor(PLAYER).get(0);

        BoundsComponent mapBounds = Mappers.BOUNDS.get(map);

        PositionComponent playerPos = Mappers.POSITION.get(player);
        BoundsComponent playerBounds = Mappers.BOUNDS.get(player);

        Polygon tempPolygon = new Polygon(playerBounds.polygon.getVertices());
        tempPolygon.setPosition(playerPos.x + velocity.x, playerPos.y + velocity.y);

        return !contains(tempPolygon.getTransformedVertices(), mapBounds.polygon);
    }

    private boolean contains(float[] vertices, Polygon polygon){
        for(int i = 0, j = 1; j < vertices.length; i += 2, j += 2){
            if(!polygon.contains(vertices[i], vertices[j])){
                return false;
            }
        }
        return true;
    }
}
