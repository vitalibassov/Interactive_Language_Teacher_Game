package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Music;
import com.vb.ilt.entity.components.MusicComponent;
import com.vb.ilt.util.Mappers;

public class MusicSystem extends IteratingSystem {

    private static final Family MUSIC = Family.all(
            MusicComponent.class
    ).get();

    public MusicSystem() {
        super(MUSIC);
    }

    private boolean toPlay = true;
    private Music music;

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MusicComponent musicComponent = Mappers.MUSIC.get(entity);
        this.music = musicComponent.music;
        if (toPlay && !this.music.isPlaying()) this.music.play();
    }

    public void setEnabled(boolean enabled){
        this.toPlay = enabled;
        this.music.stop();
    }

    public void setVolume(float volume){
        this.music.setVolume(volume);
    }
}
