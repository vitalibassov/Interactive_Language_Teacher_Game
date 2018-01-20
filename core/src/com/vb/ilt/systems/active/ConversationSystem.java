package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.entity.NPCType;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.dialog_model.Dialog;
import com.vb.ilt.entity.components.npc.ConversationComponent;
import com.vb.ilt.entity.components.npc.NPCComponent;
import com.vb.ilt.entity.components.stage.ConversationTable;
import com.vb.ilt.util.Mappers;


public class ConversationSystem extends EntitySystem implements ConversationCallback {

    private static final Logger log = new Logger(ConversationSystem.class.getName(), Logger.DEBUG);

    private final AssetManager assetManager;

    private Stage stage;
    private NPCType npcType;
    private TextureRegion region;
    private Conversation conversation;
    private final Viewport hudViewport;
    private final SpriteBatch batch;

    private HudRenderSystem hudRenderSystem;
    private PlayerControlSystem playerControlSystem;
    private MovementSystem movementSystem;

    private static final Family CONVERSATION = Family.all(
            ConversationComponent.class
    ).get();

    public ConversationSystem(AssetManager assetManager, Viewport hudViewport, SpriteBatch batch) {
        this.assetManager = assetManager;
        this.hudViewport = hudViewport;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        hudRenderSystem = engine.getSystem(HudRenderSystem.class);
        playerControlSystem = engine.getSystem(PlayerControlSystem.class);
        movementSystem = engine.getSystem(MovementSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        hudViewport.apply();
        stage.getBatch().setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();
        batch.draw(region, 0, 0, GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void setProcessing(boolean processing) {
        if (stage != null && npcType != null && processing) {
            super.setProcessing(true);
            this.hudRenderSystem.setProcessing(false);
            this.movementSystem.setProcessing(false);
            this.playerControlSystem.setProcessing(false);
        } else {
            super.setProcessing(false);
            if (this.stage != null){
                this.stage.dispose();
            }
            this.region = null;
            this.npcType = NPCType.NONE;
            this.conversation = null;
        }
    }

    public void setNpcAndRun (Entity entity){
        ConversationTable table = new ConversationTable(assetManager, this);
        TextureAtlas atlas = assetManager.get(AssetDescriptors.DIALOGS);
        NPCComponent npcComponent = Mappers.NPC.get(entity);
        ConversationComponent conversationComponent = Mappers.CONVERSATION.get(getEngine().getEntitiesFor(CONVERSATION).first());

        Conversation current = conversationComponent.conversations.first();

        this.npcType = current.getType() == npcComponent.type ? npcComponent.type : NPCType.NONE;
        if (this.npcType.isNone()){
            setProcessing(false);
            return;
        }

        this.region = atlas.findRegion(npcType.name().toLowerCase());
        this.conversation = conversationComponent.conversations.removeFirst();
        Dialog firstDialog = conversation.getNext(null);

        System.out.println(firstDialog.getNpctext());
        System.out.println(firstDialog.getPlayerAnswers());
        table.updateDialog(firstDialog.getNpctext());
        table.setAnswers(firstDialog.getPlayerAnswers());

        this.stage = new Stage(hudViewport, batch);
        this.stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
        setProcessing(true);
    }

    @Override
    public void exit() {
        this.setProcessing(false);
        this.hudRenderSystem.setProcessing(true);
        this.movementSystem.setProcessing(true);
        this.playerControlSystem.setProcessing(true);
    }

    @Override
    public void nextDialog(String answer) {
        log.debug("ANSWER: " + answer);
    }
}
