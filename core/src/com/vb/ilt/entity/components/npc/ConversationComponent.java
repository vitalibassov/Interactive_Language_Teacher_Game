package com.vb.ilt.entity.components.npc;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.vb.ilt.entity.components.dialog_model.Conversation;

import java.util.Map;

public class ConversationComponent implements Component, Pool.Poolable{

    Map<Integer, Conversation> conversations;

    @Override
    public void reset() {
        conversations = null;
    }
}
