package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.MovementComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.systems.passive.collision.WorldObjectsCollisionSystem;
import com.vb.ilt.systems.passive.collision.WorldWrapUpSystem;
import com.vb.ilt.util.Mappers;

public class MovementSystem extends IteratingSystem{

    private static final Logger log = new Logger(MovementSystem.class.getName(), Logger.DEBUG);


    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            MovementComponent.class
    ).get();

    public MovementSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        MovementComponent movement = Mappers.MOVEMENT.get(entity);

        float beforeUpdateX = position.x;
        float beforeUpdateY = position.y;

        WorldObjectsCollisionSystem collisionSystem = getEngine().getSystem(WorldObjectsCollisionSystem.class);
        WorldWrapUpSystem wrapUpSystem = getEngine().getSystem(WorldWrapUpSystem.class);
        if(!collisionSystem.checkCollision(movement.velocity) && !wrapUpSystem.outOfWorld(movement.velocity)){
            position.x += movement.velocity.x;
            position.y += movement.velocity.y;
            //log.debug("PLAYER X=" + position.x + " PLAYER Y=" + position.y);
        }
    }
}
