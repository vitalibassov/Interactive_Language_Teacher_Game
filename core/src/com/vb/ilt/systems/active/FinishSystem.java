package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.entity.components.SoundComponent;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.npc.StoryComponent;
import com.vb.ilt.ui.stages.AssessmentStage;
import com.vb.ilt.util.Mappers;

public class FinishSystem extends IteratingSystem{

    private static final Logger log = new Logger(FinishSystem.class.getName(), Logger.DEBUG);

    private final Stage finishStage;
    private float accumulator;
    private boolean inProcess = false;

    private static final Family FAMILY = Family.all(
            StoryComponent.class,
            SoundComponent.class
    ).get();

    public FinishSystem(Viewport viewport, Batch batch, AssetManager assetManager) {
        super(FAMILY);
        this.finishStage = new AssessmentStage(viewport, batch, assetManager);
    }


    @Override
    protected void processEntity(final Entity entity, float deltaTime) {
        if (GameManager.INSTANCE.isFinished() && inProcess){
            finishStage.act();
            finishStage.draw();
            Gdx.input.setInputProcessor(finishStage);
            return;
        }
        if (isReady(deltaTime)){
            Queue<Conversation> conversationQueue = Mappers.STORY.get(entity).conversations;
            log.debug("CURRENT CONVERSATION QUEUE: " + conversationQueue.size);
            if (conversationQueue.size == 0 && GameManager.INSTANCE.isPlaying() && !inProcess){
                inProcess = true;
                new Timer().scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        GameManager.INSTANCE.setStateFinished();
                        getEngine().getSystem(SoundSystem.class).playSound(entity);
                        getEngine().getSystem(MusicSystem.class).setVolume(0.25f);
                        ((AssessmentStage)finishStage).setStars();
                    }
                }, 2f);
            }
        }
    }

    private boolean isReady(float delta){
        final int INTERVAL = 3;
        accumulator += delta;
        if (accumulator >= INTERVAL){
            accumulator = 0f;
            return true;
        }else{
            return false;
        }
    }
}
