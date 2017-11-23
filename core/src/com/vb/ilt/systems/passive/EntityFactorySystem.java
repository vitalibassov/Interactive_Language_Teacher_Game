package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vb.ilt.components.BoundsComponent;
import com.vb.ilt.components.DimensionComponent;
import com.vb.ilt.components.MovementComponent;
import com.vb.ilt.components.PlayerComponent;
import com.vb.ilt.components.PositionComponent;
import com.vb.ilt.config.GameConfig;

public class EntityFactorySystem extends EntitySystem{

    private PooledEngine engine;
    private final AssetManager assetManager;
    private TextureAtlas gamePlayAtlas;

    public EntityFactorySystem(AssetManager assetManager){this.assetManager = assetManager;}

    @Override
    public boolean checkProcessing() {
        return false;
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine) engine;
    }

    public void createPlayer(){
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameConfig.WORLD_CENTER_X;
        position.y = GameConfig.WORLD_CENTER_Y;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = 1f;
        dimension.height = 2f;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        MovementComponent movement = engine.createComponent(MovementComponent.class);

        PlayerComponent player = engine.createComponent(PlayerComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        entity.add(player);
        engine.addEntity(entity);
    }
}
