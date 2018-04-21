package com.vb.ilt.ui.tables;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Map;

class MyWordsTabTable extends ListWithSearchTableBase{

    MyWordsTabTable(Skin skin) {
        super(skin, "delete");
    }

    MyWordsTabTable(Skin skin, Map<String, String> availableWords) {
        super(skin, "delete", availableWords);
    }

    @Override
    void processBtn(String wordKey, String wordValue) {
        getAvailableWords().remove(wordKey);
        updateWords();
    }
}
