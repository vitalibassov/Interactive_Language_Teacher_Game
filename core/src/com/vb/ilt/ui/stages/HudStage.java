package com.vb.ilt.ui.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.ui.tables.DictionaryTable;

import java.util.Map;

public class HudStage extends Stage{

    private static final Logger log = new Logger(HudStage.class.getName(), Logger.DEBUG);

    private final AssetManager assetManager;
    private final DictionaryTable dictTable;
    private Skin skin;

    public HudStage(AssetManager assetManager, Viewport viewport, SpriteBatch batch) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.SKIN);
        this.assetManager = assetManager;
        this.dictTable = new DictionaryTable(skin);
        init();
    }

    private void init(){
        Table mainTable = new Table();
        mainTable.defaults().pad(20);

        Table buttonTable = new Table();
        this.dictTable.setVisible(false);

        ImageButton pauseButton = new ImageButton(skin, ButtonStyleNames.PAUSE);
        final ImageButton dictButton = new ImageButton(skin, ButtonStyleNames.DICT);

        dictButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dictTable.setVisible(!dictTable.isVisible());
            }
        });

        buttonTable.add(dictButton);
        buttonTable.add(pauseButton).padLeft(60);
        buttonTable.pack();

        mainTable.add(buttonTable).top().right().expandY().expandX();
        mainTable.row();
        mainTable.add(this.dictTable).width(1000).height(900).top().right().expandX().expandY();

        mainTable.setFillParent(true);

        mainTable.pack();
        this.addActor(mainTable);
    }


    public Map<String, String> getAvailableAllWords(){
        return dictTable.getAvailableAllWords();
    }

    public Map<String, String> getAvailableMyWords(){
        return dictTable.getAvailableMyWords();
    }

    public void setAvailableMyWords(Map<String, String> words){
        dictTable.setAvailableMyWords(words);
    }

    public void updateWords(){
        dictTable.updateWords();
    }
}
