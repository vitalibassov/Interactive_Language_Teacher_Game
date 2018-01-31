package com.vb.ilt.ui.tables;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;

import java.util.Map;

public class AllWordsTabTable extends ListWithSearchTableBase{

    private static final Logger log = new Logger(AllWordsTabTable.class.getName(), Logger.DEBUG);

    private final Map<String, String> availableWordsInAllWords;

    public AllWordsTabTable(Skin skin, Map<String, String> availableWordsInAllWords) {
        super(skin, "add");
        this.availableWordsInAllWords = availableWordsInAllWords;
    }

    @Override
    void processBtn() {
        log.debug("BUTTON IS PROCESSING");
    }
}
