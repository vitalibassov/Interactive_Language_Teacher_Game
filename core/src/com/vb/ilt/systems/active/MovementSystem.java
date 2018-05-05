package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.MovementComponent;
import com.vb.ilt.entity.components.ParticlesComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.systems.passive.collision.NPCCollisionSystem;
import com.vb.ilt.systems.passive.collision.SensorCollisionSystem;
import com.vb.ilt.systems.passive.collision.WorldObjectsCollisionSystem;
import com.vb.ilt.systems.passive.collision.WorldWrapUpSystem;
import com.vb.ilt.util.Mappers;

public class MovementSystem extends IteratingSystem {

    private static final Logger log = new Logger(MovementSystem.class.getName(), Logger.DEBUG);

    private ParticlesComponent particlesComponent;

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            MovementComponent.class,
            ParticlesComponent.class
    ).get();

    public MovementSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        MovementComponent movement = Mappers.MOVEMENT.get(entity);
        this.particlesComponent = Mappers.DIRT_PARTICLES.get(entity);

        WorldObjectsCollisionSystem collisionSystem = getEngine().getSystem(WorldObjectsCollisionSystem.class);
        WorldWrapUpSystem wrapUpSystem = getEngine().getSystem(WorldWrapUpSystem.class);
        NPCCollisionSystem npcCollisionSystem = getEngine().getSystem(NPCCollisionSystem.class);
        SensorCollisionSystem sensorCollisionSystem = getEngine().getSystem(SensorCollisionSystem.class);

        this.particlesComponent.toProcess = !movement.velocity.isZero();

        if (!(sensorCollisionSystem.checkCollision(movement.velocity) || wrapUpSystem.checkCollision(movement.velocity) || npcCollisionSystem.checkCollision(movement.velocity))) {
            for (float i = 1; i <= 10; i++){
                Vector2 newVelocity = getVelocityAccordingToCollision(collisionSystem, movement.velocity.x / i, movement.velocity.y / i);
                if (!newVelocity.isZero()) {
                    position.x += newVelocity.x;
                    position.y += newVelocity.y;
                    break;
                }

            }
        }
    }

    private Vector2 getVelocityAccordingToCollision(WorldObjectsCollisionSystem collisionSystem, float velocityX, float velocityY) {
        return new Vector2(getXVelocityAccordingToCollision(collisionSystem, velocityX), getYVelocityAccordingToCollision(collisionSystem, velocityY));
    }

    private float getXVelocityAccordingToCollision(WorldObjectsCollisionSystem collisionSystem, float velocityX) {
        if (!collisionSystem.checkCollision(new Vector2(velocityX, 0f))) {
            return velocityX;
        }
        return 0;
    }

    private float getYVelocityAccordingToCollision(WorldObjectsCollisionSystem collisionSystem, float velocityY) {
        if (!collisionSystem.checkCollision(new Vector2(0f, velocityY))) {
            return velocityY;
        }
        return 0;
    }

    @Override
    public void setProcessing(boolean processing) {
        super.setProcessing(processing);
        this.particlesComponent.toProcess = false;
    }
}
