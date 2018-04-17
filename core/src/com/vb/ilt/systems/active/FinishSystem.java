package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.entity.components.SoundComponent;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.npc.StoryComponent;
import com.vb.ilt.ui.stages.AssessmentStage;
import com.vb.ilt.util.Mappers;

public class FinishSystem extends IteratingSystem{

    private static final float INTERVAL = 4f;
    private final Stage finishStage;
    private float accumulator;

    private static final Family FAMILY = Family.all(
            StoryComponent.class,
            SoundComponent.class
    ).get();

    public FinishSystem(Viewport viewport, SpriteBatch batch, AssetManager assetManager) {
        super(FAMILY);
        this.finishStage = new AssessmentStage(viewport, batch, assetManager);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        accumulator += deltaTime;
        if (GameManager.INSTANCE.isFinished()){
            finishStage.act();
            finishStage.draw();
            Gdx.input.setInputProcessor(finishStage);
            return;
        }
        if (accumulator >= INTERVAL){
            accumulator = 0;
            Queue<Conversation> conversationQueue = Mappers.STORY.get(entity).conversations;
            if (conversationQueue.size == 0 && GameManager.INSTANCE.isPlaying()){
                GameManager.INSTANCE.setStateFinished();
                getEngine().getSystem(SoundSystem.class).playSound(entity);
                getEngine().getSystem(MusicSystem.class).setVolume(0.25f);
                ((AssessmentStage)finishStage).setStars();
            }
        }
    }
}
