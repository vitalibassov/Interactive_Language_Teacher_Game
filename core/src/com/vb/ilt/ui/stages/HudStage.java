package com.vb.ilt.ui.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.ui.tables.AllWordsTabTable;
import com.vb.ilt.ui.tables.MyWordsTabTable;

import java.util.Map;

public class HudStage extends Stage{

    private final AssetManager assetManager;
    private final MyWordsTabTable myWords;
    private final AllWordsTabTable allWords;
    private Skin skin;

    public HudStage(AssetManager assetManager, Viewport viewport, SpriteBatch batch) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.SKIN);
        this.assetManager = assetManager;
        this.myWords = new MyWordsTabTable(this.skin);
        this.allWords = new AllWordsTabTable(this.skin, this.myWords.getAvailableWords());
        init();
    }

    private void init(){
        Table mainTable = new Table();
        mainTable.defaults().pad(20);

        allWords.setVisible(false);
        Table buttonTable = new Table();

        ImageButton pauseButton = new ImageButton(skin, ButtonStyleNames.PAUSE);
        ImageButton dictButton = new ImageButton(skin, ButtonStyleNames.DICT);

        dictButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                allWords.setVisible(!allWords.isVisible());
            }
        });


        buttonTable.add(dictButton);
        buttonTable.add(pauseButton).padLeft(60);
        buttonTable.pack();

        mainTable.add(buttonTable).top().right().expandY().expandX();
        mainTable.row();
        mainTable.add(allWords).top().right().expandX().expandY();

        mainTable.setFillParent(true);
        mainTable.pack();
        this.addActor(mainTable);
    }

    public Map<String, String> getAvailableWords(){
        return allWords.getAvailableWords();
    }

    public void updateWords(){
        allWords.updateWords();
    }
}
