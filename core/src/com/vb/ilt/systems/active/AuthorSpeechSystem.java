package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.npc.StoryComponent;
import com.vb.ilt.ui.stages.AuthorSpeechStage;
import com.vb.ilt.ui.stages.ExitCallback;
import com.vb.ilt.util.Mappers;

public class AuthorSpeechSystem extends IteratingSystem implements ExitCallback{

    private static final Logger log = new Logger(AuthorSpeechSystem.class.getName(), Logger.DEBUG);

    private boolean isReading = false;
    private final AuthorSpeechStage stage;
    private float accumulator = 0f;

    private static final Family FAMILY = Family.all(
            StoryComponent.class
    ).get();

    public AuthorSpeechSystem(AssetManager assetManager, Viewport hudViewport, SpriteBatch batch) {
        super(FAMILY);
        stage = new AuthorSpeechStage(
                hudViewport,
                batch,
                assetManager.get(AssetDescriptors.SKIN),
                assetManager.get(AssetDescriptors.DIALOGS).findRegion("author"),
                this
        );
    }

    private void actStage(){
        stage.act();
        stage.draw();
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Queue<Conversation> conversationQueue = Mappers.STORY.get(entity).conversations;

        if (conversationQueue.size != 0 && conversationQueue.first().getType().isPlayer() && !isReading && isReady(deltaTime)){
            systemSwitch(false);
            stage.updateText(conversationQueue.removeFirst().getNext(null).getNpctext());
            Gdx.input.setInputProcessor(stage);
            isReading = true;
        }
        if (isReading){
            actStage();
        }
    }


    @Override
    public void exit() {
        log.info("EXIT AUTHOR SPEECH");
        isReading = false;
        systemSwitch(true);
    }

    private void systemSwitch(boolean state){
        Engine engine = getEngine();
        engine.getSystem(HudSystem.class).setProcessing(state);
        engine.getSystem(PlayerControlSystem.class).setProcessing(state);
        engine.getSystem(MovementSystem.class).setProcessing(state);
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
