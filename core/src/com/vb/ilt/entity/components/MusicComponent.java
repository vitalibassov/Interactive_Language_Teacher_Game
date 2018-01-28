package com.vb.ilt.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Pool;

public class MusicComponent implements Component, Pool.Poolable{

    public Music music;

    @Override
    public void reset() {
        music = null;
    }
}
