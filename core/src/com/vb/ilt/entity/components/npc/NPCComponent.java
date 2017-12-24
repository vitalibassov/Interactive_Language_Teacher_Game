package com.vb.ilt.entity.components.npc;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.vb.ilt.entity.NPCType;

/**
 * Created by vitaa on 2017-12-24.
 */

public class NPCComponent implements Component, Pool.Poolable{

    public NPCType type;

    @Override
    public void reset() {
        type = null;
    }
}
