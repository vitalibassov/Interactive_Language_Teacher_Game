package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.entity.components.ParticlesComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.util.Mappers;

public class ParticlesSystem extends IteratingSystem {

    private static final Logger log = new Logger(ParticlesSystem.class.getName(), Logger.DEBUG);
    private final Array<ParticleEffectPool.PooledEffect> effects;
    private final Batch batch;
    private final Viewport viewport;

    private final float INTERVAL = 0.1f;
    private float accumulator;


    private static final Family FAMILY = Family.all(
            ParticlesComponent.class,
            PositionComponent.class
    ).get();


    public ParticlesSystem(Batch batch, Viewport viewport) {
        super(FAMILY);
        this.effects = new Array<>();
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = Mappers.POSITION.get(entity);
        ParticlesComponent particlesComponent = Mappers.DIRT_PARTICLES.get(entity);

        if (particlesComponent.toProcess && isReady(deltaTime) && this.effects.size <= GameConfig.PARTICLE_LIMIT) {
            ParticleEffectPool.PooledEffect effect = particlesComponent.pooledEffect.obtain();
            effect.setPosition(positionComponent.x + particlesComponent.offset.x, positionComponent.y + particlesComponent.offset.y);
            effect.start();
            this.effects.add(effect);
        }

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        for (int i = 0; i < this.effects.size; i++) {
            ParticleEffectPool.PooledEffect pooledEffect = effects.get(i);
            pooledEffect.update(deltaTime);
            batch.begin();
            pooledEffect.draw(batch);
            batch.end();
            if (pooledEffect.isComplete()) {
                this.effects.removeIndex(i);
                pooledEffect.free();
            }
        }
    }

    public boolean isReady(float delta){
        accumulator += delta;
        if (accumulator >= this.INTERVAL){
            accumulator = 0;
            return true;
        }else{
            return false;
        }
    }
}
