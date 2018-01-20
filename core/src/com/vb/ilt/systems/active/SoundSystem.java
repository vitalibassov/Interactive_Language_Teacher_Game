package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
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
        final float stepTime = 0.3f;
        Entity player = getEngine().getEntitiesFor(PLAYER).first();
        SoundComponent sound = Mappers.SOUND.get(player);
        DirectionComponent direction = Mappers.DIRECTION.get(player);
        if (currentStepDuration >= stepTime && !direction.direction.isIdle()){
            currentStepDuration = 0;
            sound.step.play();
        }
        currentStepDuration += deltaTime;
    }
}
