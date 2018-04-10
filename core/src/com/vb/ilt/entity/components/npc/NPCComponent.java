package com.vb.ilt.entity.components.npc;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.vb.ilt.entity.CharacterType;

public class NPCComponent implements Component, Pool.Poolable{

    public CharacterType type;

    @Override
    public void reset() {
        type = null;
    }
}
