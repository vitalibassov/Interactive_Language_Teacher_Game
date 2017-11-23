package com.vb.ilt.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.vb.ilt.components.BoundsComponent;
import com.vb.ilt.components.DimensionComponent;
import com.vb.ilt.components.MovementComponent;
import com.vb.ilt.components.PositionComponent;

public final class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);
    public static final ComponentMapper<MovementComponent> MOVEMENT =
            ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    private Mappers(){}
}
