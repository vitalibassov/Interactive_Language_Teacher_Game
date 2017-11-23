package com.vb.ilt.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.vb.ilt.components.BoundsComponent;

public final class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    private Mappers(){}
}
