package com.vb.ilt.ui.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
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

        Table buttonTable = new Table();

        final Table dictTable = new Table();
        final WidgetGroup tabContainer = new WidgetGroup();

        dictTable.setVisible(false);

        ImageButton pauseButton = new ImageButton(skin, ButtonStyleNames.PAUSE);
        final ImageButton dictButton = new ImageButton(skin, ButtonStyleNames.DICT);

        TextButton allWordsTab = new TextButton("All Words", skin);
        TextButton myWordsTab = new TextButton("My Words", skin);

        dictButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dictTable.setVisible(!dictTable.isVisible());
            }
        });

        allWordsTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tabContainer.removeActor(myWords);
                tabContainer.addActor(allWords);
            }
        });

        myWordsTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tabContainer.removeActor(allWords);
                tabContainer.addActor(myWords);
            }
        });

        buttonTable.add(dictButton);
        buttonTable.add(pauseButton).padLeft(60);
        buttonTable.pack();

        this.allWords.debug();

        //this.allWords.setSize(800, 600);
        dictTable.setBackground(this.skin.getDrawable("panel"));
        dictTable.add(allWordsTab).growX().pad(30);
        dictTable.add(myWordsTab).growX().pad(30);
        dictTable.row();

        dictTable.add(tabContainer).grow().colspan(2);

        dictTable.pack();
        dictTable.debug();

        mainTable.add(buttonTable).top().right().expandY().expandX();
        mainTable.row();
        mainTable.add(dictTable).width(1000).height(800).top().right().expandX().expandY();

        mainTable.setFillParent(true);

        mainTable.pack();
        mainTable.debug();
        this.addActor(mainTable);
    }

    public Map<String, String> getAvailableWords(){
        return allWords.getAvailableWords();
    }

    public void updateWords(){
        allWords.updateWords();
    }
}
