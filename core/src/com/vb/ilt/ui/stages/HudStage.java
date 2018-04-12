package com.vb.ilt.ui.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.systems.active.HudSystem;
import com.vb.ilt.systems.active.MovementSystem;
import com.vb.ilt.systems.active.PlayerControlSystem;
import com.vb.ilt.ui.tables.DictionaryTable;

import java.util.Map;

public class HudStage extends Stage{

    private static final Logger log = new Logger(HudStage.class.getName(), Logger.DEBUG);

    private final AssetManager assetManager;
    private final DictionaryTable dictTable;
    private PauseCallback pauseCallback;
    private Skin skin;


    public HudStage(AssetManager assetManager, Viewport viewport, SpriteBatch batch) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.SKIN);
        this.assetManager = assetManager;
        this.dictTable = new DictionaryTable(skin);
        init();
    }

    public HudStage(AssetManager assetManager, Viewport viewport, SpriteBatch batch, Map<String, String> allWords, Map<String, String> myWords) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.SKIN);
        this.assetManager = assetManager;
        this.dictTable = new DictionaryTable(skin, allWords, myWords);
        init();
    }

    private void init(){
        Table mainTable = new Table();
        mainTable.defaults().pad(20);

        Table buttonTable = new Table();
        this.dictTable.setVisible(false);

        final ImageButton pauseButton = new ImageButton(skin, ButtonStyleNames.PAUSE);
        final ImageButton dictButton = new ImageButton(skin, ButtonStyleNames.DICT);

        dictButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dictTable.setVisible(!dictTable.isVisible());
            }
        });

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseCallback.setSystemsDisabledAndShowPauseMenu(
                        MovementSystem.class,
                        HudSystem.class,
                        PlayerControlSystem.class
                );
            }
        });

        Table scoreTable = new Table();
        Image image = new Image(assetManager.get(AssetDescriptors.SKIN).getDrawable(RegionNames.COIN));
        Label score = new Label("2000", skin);
        score.setFontScale(2f);

        scoreTable.add(image);
        scoreTable.add(score).padLeft(15f);
        scoreTable.pack();

        buttonTable.add(dictButton);
        buttonTable.add(pauseButton).padLeft(60);
        buttonTable.pack();

        mainTable.add(scoreTable).top().left().expandY().expandX();
        mainTable.add(buttonTable).top().right().expandY().expandX();
        mainTable.row();
        mainTable.add(this.dictTable).width(1000).height(900).top().right().expandX().expandY();

        mainTable.setFillParent(true);

        mainTable.pack();
        this.addActor(mainTable);
    }

    public void updateWords(){
        dictTable.updateWords();
    }

    public void setPauseCallback(PauseCallback pauseCallback) {
        this.pauseCallback = pauseCallback;
    }
}
