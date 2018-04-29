package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.entity.components.DirectionComponent;
import com.vb.ilt.entity.components.PlayerComponent;
import com.vb.ilt.entity.components.SoundComponent;
import com.vb.ilt.util.Mappers;

public class SoundSystem extends EntitySystem{

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            SoundComponent.class,
            DirectionComponent.class
    ).get();

    private float currentStepDuration;

    @Override
    public void update(float deltaTime) {
        playerWalking(deltaTime);
    }

    private void playerWalking(float deltaTime){
        final float stepTime = 0.40f;
        Entity player = getEngine().getEntitiesFor(PLAYER).first();
        SoundComponent sound = Mappers.SOUND.get(player);
        DirectionComponent direction = Mappers.DIRECTION.get(player);
        if (!direction.direction.isIdle()) {
            if (currentStepDuration >= stepTime) {
                currentStepDuration = 0;
                sound.sound.play();
            }
            currentStepDuration += deltaTime;
        }
    }

    public void playSound(Entity entity){
        playSound(entity, GameConfig.SOUNDS_VOLUME);
    }

    public void playSound(Entity entity, float volume){
        if (Mappers.SOUND.has(entity)){
            SoundComponent sound = Mappers.SOUND.get(entity);
            sound.sound.play(volume);
        }
    }
}
