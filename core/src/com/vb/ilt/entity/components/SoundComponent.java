package com.vb.ilt.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;

public class SoundComponent implements Component, Pool.Poolable{

    public Sound sound;

    @Override
    public void reset() {
        sound = null;
    }
}
