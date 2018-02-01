package com.vb.ilt.ui.tables;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyWordsTabTable extends ListWithSearchTableBase{

    public MyWordsTabTable(Skin skin) {
        super(skin, "delete");
    }

    @Override
    void processBtn(String wordKey, String wordValue) {
        getAvailableWords().remove(wordKey);
        updateWords();
    }
}
