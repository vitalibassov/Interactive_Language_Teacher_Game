package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.vb.ilt.components.BoundsComponent;
import com.vb.ilt.components.PositionComponent;
import com.vb.ilt.util.Mappers;

public class BoundsSystem extends IteratingSystem{

    private static final Family FAMILY = Family.all(
            BoundsComponent.class,
            //DimensionComponent.class,
            PositionComponent.class
    ).get();

    public BoundsSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bounds = Mappers.BOUNDS.get(entity);
        //DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);

        //bounds.rectangle.setSize(dimension.width, dimension.height);
        bounds.rectangle.setPosition(position.x, position.y);
    }
}
