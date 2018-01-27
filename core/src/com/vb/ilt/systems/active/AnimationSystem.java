package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.vb.ilt.entity.components.AnimationComponent;
import com.vb.ilt.entity.components.TextureComponent;
import com.vb.ilt.util.Mappers;

public class AnimationSystem extends IteratingSystem{

    private static final Family FAMILY = Family.all(
            AnimationComponent.class,
            TextureComponent.class
    ).get();

    public AnimationSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation = Mappers.ANIMATION.get(entity);
        TextureComponent texture = Mappers.TEXTURE.get(entity);

        texture.region = animation.animations.get(animation.getAnimationIndex()).getKeyFrame(animation.animationTime);
        animation.animationTime += deltaTime;
    }
}
