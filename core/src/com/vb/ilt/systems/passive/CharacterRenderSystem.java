package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vb.ilt.entity.components.DimensionComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.TextureComponent;
import com.vb.ilt.entity.components.ZOrderComponent;
import com.vb.ilt.util.Mappers;
import com.vb.ilt.util.ZOrderComparator;

/**
 * Created by vitaa on 2017-12-24.
 */

public class CharacterRenderSystem extends SortedIteratingSystem {

    private final SpriteBatch batch;
    private static final Family GAME_OBJECTS_FAMILY = Family.all(
            PositionComponent.class,
            TextureComponent.class,
            DimensionComponent.class,
            ZOrderComponent.class
    ).get();

    @Override
    public boolean checkProcessing() {
        return false;
    }

    public CharacterRenderSystem(SpriteBatch batch) {
        super(GAME_OBJECTS_FAMILY, ZOrderComparator.INSTANCE);
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        super.forceSort();
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        TextureComponent texture = Mappers.TEXTURE.get(entity);
        batch.draw(texture.region,
                position.x, position.y,
                dimension.width, dimension.height
        );
    }
}
