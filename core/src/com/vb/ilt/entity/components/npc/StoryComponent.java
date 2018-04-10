package com.vb.ilt.entity.components.npc;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Queue;
import com.vb.ilt.entity.components.dialog_model.Conversation;

public class StoryComponent implements Component, Pool.Poolable{

    public Queue<Conversation> conversations;

    @Override
    public void reset() {
        conversations = null;
    }
}
