package com.vb.ilt.entity.components.hud;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

public class StageComponent implements Component, Pool.Poolable{

    public Stage stage;

    @Override
    public void reset() {
        stage = null;
    }
}
