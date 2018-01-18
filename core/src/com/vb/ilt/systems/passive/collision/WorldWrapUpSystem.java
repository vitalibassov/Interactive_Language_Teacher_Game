package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.world.TiledMapComponent;

public class WorldWrapUpSystem extends CollisionBase {

    private static final Logger log = new Logger(WorldWrapUpSystem.class.getName(), Logger.DEBUG);

    private static final Family WORLD = Family.all(
            TiledMapComponent.class,
            BoundsComponent.class
    ).get();

    public WorldWrapUpSystem() {
        super(WORLD);
    }

    @Override
    public boolean checkCollision(Vector2 velocity) {
        return !super.checkCollision(velocity);
    }

    @Override
    protected boolean contains(float[] vertices, Polygon polygon){
        for(int i = 0, j = 1; j < vertices.length; i += 2, j += 2){
            if(!polygon.contains(vertices[i], vertices[j])){
                return false;
            }
        }
        return true;
    }
}
