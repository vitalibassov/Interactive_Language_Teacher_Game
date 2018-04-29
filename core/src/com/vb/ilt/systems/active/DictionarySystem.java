package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.entity.components.DictionaryComponent;
import com.vb.ilt.entity.components.hud.HudComponent;
import com.vb.ilt.entity.components.hud.StageComponent;
import com.vb.ilt.entity.components.npc.StoryComponent;
import com.vb.ilt.ui.stages.HudStage;
import com.vb.ilt.util.Mappers;

import java.util.List;
import java.util.Map;

public class DictionarySystem extends IntervalSystem{

    private static final float INTERVAL = 1.0f;
    private int currentConversationSize;

    private static final Family STORY = Family.all(
            StoryComponent.class
    ).get();

    private static final Family DICT = Family.all(
            DictionaryComponent.class
    ).get();

    private static final Family HUD_STAGE = Family.all(
            StageComponent.class,
            HudComponent.class
    ).get();

    public DictionarySystem() {
        super(INTERVAL);
    }

    @Override
    protected void updateInterval() {
        Entity dictionaryEntity = getEngine().getEntitiesFor(DICT).first();
        Entity storyEntity = getEngine().getEntitiesFor(STORY).first();
        StoryComponent storyComponent = Mappers.STORY.get(storyEntity);
        final int conversationsSize = storyComponent.conversations.size;

        if (currentConversationSize != conversationsSize && conversationsSize != 0){
            DictionaryComponent dictionaryComponent = Mappers.DICT.get(dictionaryEntity);
            addNewWordsToDictionary(storyComponent.conversations.first().getAllText(), dictionaryComponent.allWords);
            currentConversationSize = storyComponent.conversations.size;
            ((HudStage)Mappers.STAGE.get(getEngine().getEntitiesFor(HUD_STAGE).first()).stage).updateWords();
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
