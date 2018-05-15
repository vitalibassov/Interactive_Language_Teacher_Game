package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.VelocityComponent;

import org.junit.Assert;
import org.junit.Test;

public class MovementSystemTest {

    private final Vector2 velocity = new Vector2(2, -2);

    @Test
    public void processEntity() {
        Engine engine = new PooledEngine();
        Entity entity = engine.createEntity();
        VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        velocityComponent.velocity = this.velocity;
        entity.add(velocityComponent);

        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        entity.add(positionComponent);

        engine.addEntity(entity);
        engine.addSystem(new MovementSystem());

        engine.update(0.1f);
        Assert.assertEquals(2f, positionComponent.x, 0.1f);
        Assert.assertEquals(-2f, positionComponent.y, 0.1f);
    }
}