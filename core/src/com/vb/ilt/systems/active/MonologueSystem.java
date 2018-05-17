package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.npc.StoryComponent;
import com.vb.ilt.ui.stages.AuthorSpeechStage;
import com.vb.ilt.ui.stages.ExitCallback;
import com.vb.ilt.ui.stages.MainCharacterSpeechStage;
import com.vb.ilt.ui.stages.MonologueStage;
import com.vb.ilt.util.Mappers;

public class MonologueSystem extends IteratingSystem implements ExitCallback{

    private boolean isReading = false;
    private final MonologueStage authorSpeechStage;
    private final MonologueStage mainCharacterSpeechStage;
    private float accumulator = 0f;

    private Queue<Conversation> conversationQueue;

    private MonologueStage monologueStage;

    private static final Family FAMILY = Family.all(
            StoryComponent.class
    ).get();

    public MonologueSystem(AssetManager assetManager, Viewport hudViewport, Batch batch) {
        super(FAMILY);
        authorSpeechStage = new AuthorSpeechStage(
                hudViewport,
                batch,
                assetManager,
                this
        );
        mainCharacterSpeechStage = new MainCharacterSpeechStage(
                hudViewport,
                batch,
                assetManager,
                this
        );
    }

    private void actStage(){
        this.monologueStage.act();
        this.monologueStage.draw();
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.conversationQueue = Mappers.STORY.get(entity).conversations;
        if (conversationQueue.size != 0 && !isReading && isReady(deltaTime)){
            if (conversationQueue.first().getType().isAuthor()){
                monologueStage = authorSpeechStage;
            }else if(conversationQueue.first().getType().isProtagonist()){
                monologueStage = mainCharacterSpeechStage;
            }else {
                return;
            }
            systemSwitch(false);
            this.monologueStage.updateText(conversationQueue.first().getCurrentDialog().getNpctext());
            this.monologueStage.fadeIn();
            this.monologueStage.postponeButtonAppearance();
            Gdx.input.setInputProcessor(this.monologueStage);
            isReading = true;
        }
        if (isReading){
            actStage();
        }
    }


    @Override
    public void exit() {
        isReading = false;
        this.conversationQueue.removeFirst();
        systemSwitch(true);
    }

    private void systemSwitch(boolean state){
        Engine engine = getEngine();
        engine.getSystem(HudSystem.class).setProcessing(state);
        engine.getSystem(PlayerControlSystem.class).setProcessing(state);
        engine.getSystem(MovementSystem.class).setProcessing(state);
        System.out.println("SWITCHING SYSTEMS: " + state);
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
