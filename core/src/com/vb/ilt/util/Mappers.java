package com.vb.ilt.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.vb.ilt.entity.components.AnimationComponent;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.DictionaryComponent;
import com.vb.ilt.entity.components.DimensionComponent;
import com.vb.ilt.entity.components.DirectionComponent;
import com.vb.ilt.entity.components.MovementComponent;
import com.vb.ilt.entity.components.MusicComponent;
import com.vb.ilt.entity.components.ParticlesComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.SoundComponent;
import com.vb.ilt.entity.components.TextureComponent;
import com.vb.ilt.entity.components.ZOrderComponent;
import com.vb.ilt.entity.components.hud.ControlsComponent;
import com.vb.ilt.entity.components.hud.StageComponent;
import com.vb.ilt.entity.components.npc.StoryComponent;
import com.vb.ilt.entity.components.npc.NPCComponent;
import com.vb.ilt.entity.components.world.PortalSensorComponent;
import com.vb.ilt.entity.components.world.PortalSensorSpawnComponent;
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
    public static final ComponentMapper<AnimationComponent> ANIMATION =
            ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<ZOrderComponent> Z_ORDER =
            ComponentMapper.getFor(ZOrderComponent.class);
    public static final ComponentMapper<NPCComponent> NPC =
            ComponentMapper.getFor(NPCComponent.class);
    public static final ComponentMapper<StoryComponent> STORY =
            ComponentMapper.getFor(StoryComponent.class);
    public static final ComponentMapper<SoundComponent> SOUND =
            ComponentMapper.getFor(SoundComponent.class);
    public static final ComponentMapper<DirectionComponent> DIRECTION =
            ComponentMapper.getFor(DirectionComponent.class);
    public static final ComponentMapper<PortalSensorComponent> PORTAL_SENSOR =
            ComponentMapper.getFor(PortalSensorComponent.class);
    public static final ComponentMapper<PortalSensorSpawnComponent> PORTAL_SENSOR_SPAWN =
            ComponentMapper.getFor(PortalSensorSpawnComponent.class);
    public static final ComponentMapper<MusicComponent> MUSIC =
            ComponentMapper.getFor(MusicComponent.class);
    public static final ComponentMapper<StageComponent> STAGE =
            ComponentMapper.getFor(StageComponent.class);
    public static final ComponentMapper<DictionaryComponent> DICT =
            ComponentMapper.getFor(DictionaryComponent.class);
    public static final ComponentMapper<ParticlesComponent> DIRT_PARTICLES =
            ComponentMapper.getFor(ParticlesComponent.class);

    private Mappers(){}
}
