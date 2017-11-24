package com.vb.ilt.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

public class TextureComponent implements Component, Pool.Poolable{

    public Texture texture;

    @Override
    public void reset() {
        texture = null;
    }
}
