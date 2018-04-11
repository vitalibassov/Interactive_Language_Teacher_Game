package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Queue;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.npc.StoryComponent;
import com.vb.ilt.util.Mappers;

public class FinishSystem extends IntervalSystem{

    private static final float INTERVAL = 4f;

    private static final Family FAMILY = Family.all(
            StoryComponent.class
    ).get();

    public FinishSystem() {
        super(INTERVAL);
    }

    @Override
    protected void updateInterval() {
        Queue<Conversation> conversationQueue = Mappers.STORY.get(getEngine().getEntitiesFor(FAMILY).first()).conversations;
        if (conversationQueue.size == 0 && GameManager.INSTANCE.isPlaying()){
            GameManager.INSTANCE.setStateFinished();
        }
    }
}
