package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.entity.NPCType;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.dialog_model.Dialog;
import com.vb.ilt.entity.components.npc.ConversationComponent;
import com.vb.ilt.entity.components.npc.NPCComponent;
import com.vb.ilt.screen.stage.ConversationTable;
import com.vb.ilt.util.Mappers;


public class ConversationSystem extends EntitySystem implements ConversationCallback {

    private static final Logger log = new Logger(ConversationSystem.class.getName(), Logger.DEBUG);

    private final AssetManager assetManager;

    private ConversationTable npcConv;
    private Queue<Conversation> conversations;
    private Stage stage;
    private NPCType npcType;
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
        stage.act();
        stage.draw();
    }

    public boolean setNpcAndRun (Entity entity){

        NPCComponent npcComponent = Mappers.NPC.get(entity);
        this.conversations = Mappers.CONVERSATION.get(getEngine().getEntitiesFor(CONVERSATION).first()).conversations;

        if (this.conversations.size != 0) {
            Conversation current = this.conversations.first();
            this.npcType = current.getType() == npcComponent.type ? npcComponent.type : NPCType.NONE;
        }else{
            this.npcType = NPCType.NONE;
        }

        if (this.npcType.isNone()){
            setProcessing(false);
            return false;
        }

        buildStage(this.conversations);
        setProcessing(true);
        return true;
    }

    private void buildStage(Queue<Conversation> conversations) {
        TextureAtlas atlas = assetManager.get(AssetDescriptors.DIALOGS);
        this.npcConv = new ConversationTable(assetManager, atlas.findRegion(this.npcType.name().toLowerCase()), this);

        conversations.first().setToStart();
        Dialog firstDialog = conversations.first().getNext(null);

        this.npcConv.updateDialog(firstDialog.getNpctext());
        this.npcConv.setAnswers(firstDialog.getPlayerAnswers());

        this.stage = new Stage(hudViewport, batch);
        this.stage.addActor(npcConv);

        Gdx.input.setInputProcessor(stage);
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
        Dialog dialog = this.conversations.first().getNext(answer);
        if (dialog == null){
            this.conversations.removeFirst();
            exit();
            return;
        }
        npcConv.updateDialog(dialog.getNpctext());
        npcConv.setAnswers(dialog.getPlayerAnswers());
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
            this.npcType = NPCType.NONE;
            this.conversations = null;
            this.npcConv = null;
        }
    }
}
