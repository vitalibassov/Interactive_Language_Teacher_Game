package com.vb.ilt.ui.tables;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Map;

public class AllWordsTabTable extends ListWithSearchTableBase{

    private final Map<String, String> availableWordsInMyWords;

    AllWordsTabTable(Skin skin, Map<String, String> availableWordsInMyWords) {
        super(skin, "add");
        this.availableWordsInMyWords = availableWordsInMyWords;
    }

    AllWordsTabTable(Skin skin, Map<String, String> availableWordsInMyWords, Map<String, String> availableWords) {
        super(skin, "add", availableWords);
        this.availableWordsInMyWords = availableWordsInMyWords;
    }

    @Override
    void processBtn(String wordKey, String wordValue) {
        availableWordsInMyWords.put(wordKey, wordValue);
        updateWords();
    }

    @Override
    protected boolean checkWordKey(String wordKey) {
        //log.debug("WORD KEY= " + wordKey);
        //log.debug("AVAILABLE WORDS IS NULL= " + (availableWordsInMyWords == null));
        return wordKey != null && !availableWordsInMyWords.containsKey(wordKey);
    }
}
