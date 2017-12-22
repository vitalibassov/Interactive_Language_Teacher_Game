package com.vb.ilt.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.DimensionComponent;
import com.vb.ilt.entity.components.MovementComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.TextureComponent;
import com.vb.ilt.entity.components.hud.ControlsComponent;
import com.vb.ilt.entity.components.world.TiledMapComponent;
import com.vb.ilt.entity.components.world.TiledMapRendererComponent;

public final class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);
    public static final ComponentMapper<MovementComponent> MOVEMENT =
            ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<TiledMapComponent> MAP =
            ComponentMapper.getFor(TiledMapComponent.class);
    public static final ComponentMapper<TiledMapRendererComponent> MAP_RENDERER =
            ComponentMapper.getFor(TiledMapRendererComponent.class);
    public static final ComponentMapper<ControlsComponent> CONTROLS =
            ComponentMapper.getFor(ControlsComponent.class);

    private Mappers(){}
}
