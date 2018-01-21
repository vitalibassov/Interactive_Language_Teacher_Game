package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.util.Mappers;

public class BoundsSystem extends IteratingSystem{

    private static final Family FAMILY = Family.all(
            BoundsComponent.class,
            PositionComponent.class
    ).get();

    public BoundsSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bounds = Mappers.BOUNDS.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);
        bounds.polygon.setPosition(position.x, position.y);
    }
}
