package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.vb.ilt.entity.components.MusicComponent;
import com.vb.ilt.util.Mappers;

public class MusicSystem extends IteratingSystem {

    private static final Family MUSIC = Family.all(
            MusicComponent.class
    ).get();

    public MusicSystem() {
        super(MUSIC);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MusicComponent musicComponent = Mappers.MUSIC.get(entity);
        if (!musicComponent.music.isPlaying()) musicComponent.music.play();
    }
}
