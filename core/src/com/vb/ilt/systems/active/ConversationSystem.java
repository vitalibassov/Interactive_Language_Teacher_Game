package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.entity.CharacterType;
import com.vb.ilt.entity.components.DictionaryComponent;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.dialog_model.Dialog;
import com.vb.ilt.entity.components.hud.HudComponent;
import com.vb.ilt.entity.components.hud.StageComponent;
import com.vb.ilt.entity.components.npc.StoryComponent;
import com.vb.ilt.entity.components.npc.NPCComponent;
import com.vb.ilt.ui.stages.ConversationCallback;
import com.vb.ilt.ui.stages.ConversationStage;
import com.vb.ilt.ui.stages.HudStage;
import com.vb.ilt.util.Mappers;

import java.util.List;
import java.util.Map;

public class ConversationSystem extends EntitySystem implements ConversationCallback {

    private static final Logger log = new Logger(ConversationSystem.class.getName(), Logger.DEBUG);

    private final AssetManager assetManager;

    private ConversationStage npcConv;
    private Queue<Conversation> conversations;
    private CharacterType characterType;
    private HudStage hudStage;
    private DictionaryComponent dictionaryComponent;
    private final Viewport hudViewport;
    private final SpriteBatch batch;

    private HudSystem hudSystem;
    private PlayerControlSystem playerControlSystem;
    private MovementSystem movementSystem;

    private static final Family STORY = Family.all(
            StoryComponent.class
    ).get();

    private static final Family DICT = Family.all(
            DictionaryComponent.class
    ).get();

    private static final Family HUD_STAGE = Family.all(
            HudComponent.class,
            StageComponent.class
    ).get();

    public ConversationSystem(AssetManager assetManager, Viewport hudViewport, SpriteBatch batch) {
        this.assetManager = assetManager;
        this.hudViewport = hudViewport;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        hudSystem = engine.getSystem(HudSystem.class);
        playerControlSystem = engine.getSystem(PlayerControlSystem.class);
        movementSystem = engine.getSystem(MovementSystem.class);
        hudStage = (HudStage) Mappers.STAGE.get(engine.getEntitiesFor(HUD_STAGE).first()).stage;
        dictionaryComponent = Mappers.DICT.get(engine.getEntitiesFor(DICT).first());
    }

    @Override
    public void update(float deltaTime) {
        npcConv.act();
        npcConv.draw();
    }

    public boolean setNpcAndRun (Entity entity){
        NPCComponent npcComponent = Mappers.NPC.get(entity);
        this.conversations = Mappers.STORY.get(getEngine().getEntitiesFor(STORY).first()).conversations;

        if (this.conversations.size != 0) {
            Conversation current = this.conversations.first();
            this.characterType = current.getType() == npcComponent.type ? npcComponent.type : CharacterType.NONE;
        }else{
            this.characterType = CharacterType.NONE;
        }

        if (this.characterType.isNone()){
            setProcessing(false);
            return false;
        }

        buildStage(this.conversations);
        setProcessing(true);
        return true;
    }

    private void buildStage(Queue<Conversation> conversations) {
        TextureAtlas atlas = assetManager.get(AssetDescriptors.PANELS);

        log.debug("ALL WORDS IS NULL= " + (dictionaryComponent.allWords == null));
        log.debug("MY WORDS IS NULL= " + (dictionaryComponent.myWords == null));
        this.npcConv = new ConversationStage(hudViewport, batch,
                assetManager.get(AssetDescriptors.UI_SKIN),
                atlas.findRegion(this.characterType.name().toLowerCase()), this);
        this.npcConv.setAvailableAllWords(dictionaryComponent.allWords);
        this.npcConv.setAvailableMyWords(dictionaryComponent.myWords);
        this.npcConv.updateWords();

        conversations.first().setToStart();
        Dialog firstDialog = conversations.first().getNext(null);

        this.npcConv.updateDialog(firstDialog.getNpctext());
        this.npcConv.setAnswers(firstDialog.getPlayerAnswers());

        Gdx.input.setInputProcessor(npcConv);
    }

    @Override
    public void exit() {
        GameManager.INSTANCE.dropTempScore();
        this.hudSystem.setProcessing(true);
        this.movementSystem.setProcessing(true);
        this.playerControlSystem.setProcessing(true);
        this.dictionaryComponent.myWords.clear();
        this.dictionaryComponent.myWords.putAll(this.npcConv.getAvailableMyWords());
        this.hudStage.updateWords();
        this.setProcessing(false);
    }

    @Override
    public void nextDialog(String answer) {
        log.debug("ANSWER: " + answer);
        GameManager.INSTANCE.increaseTempScoreBy(this.conversations.first().getCurrentDialog().getScore(answer));
        Conversation conversation = this.conversations.first();
        Dialog dialog = conversation.getNext(answer);

        if (dialog == null){
            Conversation.NonConversationalAction nonConversationalAction = conversation.checkForNonConversationalAction();
            if (nonConversationalAction.isQuitConversation()){
                exit();
            }else if (nonConversationalAction.isFinishConversation()){
                GameManager.INSTANCE.commitTempScoreAmount();
                Conversation finishedConv = this.conversations.removeFirst();
                Entity dictionaryEntity = getEngine().getEntitiesFor(DICT).first();
                DictionaryComponent dictionaryComponent = Mappers.DICT.get(dictionaryEntity);
                addNewWordsToDictionary(finishedConv.getAllText(), dictionaryComponent.allWords);
                exit();
            }else{
                throw new RuntimeException("WE'VE GOT A PROBLEM...");
            }
            return;
        }
        npcConv.updateDialog(dialog.getNpctext());
        npcConv.setAnswers(dialog.getPlayerAnswers());
    }

    @Override
    public void setProcessing(boolean processing) {
        if (npcConv != null && characterType != null && processing) {
            super.setProcessing(true);
            this.hudSystem.setProcessing(false);
            this.movementSystem.setProcessing(false);
            this.playerControlSystem.setProcessing(false);
        } else {
            super.setProcessing(false);
            if (this.npcConv != null){
                this.npcConv.dispose();
            }
            this.characterType = CharacterType.NONE;
            this.conversations = null;
            this.npcConv = null;
        }
    }

    private void addNewWordsToDictionary (List<String> text, Map<String, String> dictionary){
        for (String s : text){
            for (String word : s.toLowerCase().split(" ")){
                String formattedWord = word.replaceAll("\\W", "");
                if (dictionary.get(formattedWord) == null){
                    String result = GameManager.INSTANCE.getBigDictionary().get(formattedWord);
                    if (result != null) {
                        dictionary.put(formattedWord, GameManager.INSTANCE.getBigDictionary().get(formattedWord));
                    }
                }
            }
        }
    }
}
