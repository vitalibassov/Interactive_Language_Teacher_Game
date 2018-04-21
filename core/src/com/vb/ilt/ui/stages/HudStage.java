package com.vb.ilt.ui.stages;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.systems.active.HudSystem;
import com.vb.ilt.systems.active.MonologueSystem;
import com.vb.ilt.systems.active.MovementSystem;
import com.vb.ilt.systems.active.PlayerControlSystem;
import com.vb.ilt.ui.tables.DictionaryTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HudStage extends Stage{

    private int currentScore = 0;

    private final AssetManager assetManager;
    private final DictionaryTable dictTable;
    private Label score;
    private PauseCallback pauseCallback;
    private Skin skin;


    public HudStage(AssetManager assetManager, Viewport viewport, Batch batch) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.assetManager = assetManager;
        this.dictTable = new DictionaryTable(skin);
        init();
    }

    public HudStage(AssetManager assetManager, Viewport viewport, Batch batch, Map<String, String> allWords, Map<String, String> myWords) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.assetManager = assetManager;
        this.dictTable = new DictionaryTable(skin, allWords, myWords);
        init();
    }

    private void init(){
        Table mainTable = new Table();
        mainTable.defaults().pad(20);
        Table buttonTable = new Table();
        this.dictTable.setVisible(false);

        final Button pauseButton = new ImageButton(skin, ButtonStyleNames.PAUSE);
        final Button dictButton = new ImageButton(skin, ButtonStyleNames.DICT);

        dictButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dictTable.setVisible(!dictTable.isVisible());
                if (!dictTable.isVisible()){
                    dictTable.hideKeyboard();
                }
            }
        });

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                List<Class<? extends EntitySystem>> list = new ArrayList<>();
                list.add(MovementSystem.class);
                list.add(HudSystem.class);
                list.add(PlayerControlSystem.class);
                list.add(MonologueSystem.class);
                pauseCallback.setSystemsDisabledAndShowPauseMenu(list);
            }
        });

        Table scoreTable = new Table();
        Image image = new Image(assetManager.get(AssetDescriptors.UI_SKIN).getDrawable(RegionNames.COIN));
        score = new Label("0", skin);
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
        mainTable.add(this.dictTable).width(1000).height(900).top().right().expandX().expandY().colspan(2);

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

    @Override
    public void act() {
        super.act();
        final int actualScore = GameManager.INSTANCE.getScore();
        if (currentScore < actualScore) {
            currentScore += 5;
            if (currentScore > actualScore)setScore(actualScore);
            else setScore(currentScore);
        }
    }

    private void setScore(int amount){
        score.setText(""+amount);
    }
}
