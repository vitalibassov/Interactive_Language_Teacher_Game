package com.vb.ilt.ui.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.systems.active.AuthorSpeechSystem;
import com.vb.ilt.systems.active.HudSystem;
import com.vb.ilt.systems.active.MovementSystem;
import com.vb.ilt.systems.active.PlayerControlSystem;

public class PauseStage extends Stage {

    private static final Logger log = new Logger(ConversationStage.class.getName(), Logger.DEBUG);

    private final Skin skin;
    private PauseCallback pauseCallback;

    private TextureRegion pausePanel;
    private TextureRegion background;


    public PauseStage(Viewport viewport, SpriteBatch batch, AssetManager assetManager, PauseCallback pauseCallback) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.SKIN);
        this.pausePanel = assetManager.get(AssetDescriptors.PANELS).findRegion(RegionNames.PAUSE_PANEL);
        this.background = assetManager.get(AssetDescriptors.PANELS).findRegion(RegionNames.PAUSE_BACKGROUND);
        this.pauseCallback = pauseCallback;
        init();
    }

    private void init(){
        Table mainTable = new Table();
        mainTable.defaults().pad(20f);

        //this.npcText.setFontScale(2);

        Table container = new Table();
        Table buttonTable = new Table();
        buttonTable.pad(20f);

        TextButton exitButton = new TextButton("QUIT", skin);
        TextButton resumeButton = new TextButton("RESUME", skin);

        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseCallback.setSystemsEnabledAndClosePauseMenu(
                        MovementSystem.class,
                        HudSystem.class,
                        PlayerControlSystem.class,
                        AuthorSpeechSystem.class
                );
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        buttonTable.add(exitButton).padRight(10f);
        buttonTable.add(resumeButton).padLeft(10f);
        buttonTable.pack();

        mainTable.add(buttonTable).center().padTop(60f);

        mainTable.setBackground(new TextureRegionDrawable(pausePanel));
        mainTable.center();
        mainTable.setSize(720f, 400f);

        mainTable.pack();

        container.setFillParent(true);
        container.add(mainTable);
        container.background(new TextureRegionDrawable(background));

        this.addActor(container);
    }
}

