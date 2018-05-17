package com.vb.ilt.ui.stages;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.systems.active.HudSystem;
import com.vb.ilt.systems.active.MonologueSystem;
import com.vb.ilt.systems.active.MovementSystem;
import com.vb.ilt.systems.active.PlayerControlSystem;

import java.util.ArrayList;
import java.util.List;

public class PauseStage extends Stage {

    private static final Logger log = new Logger(ConversationStage.class.getName(), Logger.DEBUG);

    private final Skin skin;
    private PauseCallback pauseCallback;

    private TextureRegion pausePanel;
    private TextureRegion background;

    private Table container;


    public PauseStage(Viewport viewport, Batch batch, AssetManager assetManager, PauseCallback pauseCallback) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.pausePanel = assetManager.get(AssetDescriptors.PANELS).findRegion(RegionNames.PAUSE_PANEL);
        this.background = assetManager.get(AssetDescriptors.PANELS).findRegion(RegionNames.PAUSE_BACKGROUND);
        this.pauseCallback = pauseCallback;
        init();
    }

    public void smoothlyAppear(float duration){
        this.container.addAction(Actions.alpha(1f, duration));
    }

    public void smoothlyDisappear(float duration){
        this.container.addAction(Actions.alpha(0f, duration));
    }

    private void init(){
        Table mainTable = new Table();
        mainTable.defaults().pad(20f);

        //this.npcText.setFontScale(2);

        container = new Table();
        Table buttonTable = new Table();
        buttonTable.pad(20f);

        Button exitButton = new TextButton("QUIT", skin);
        Button resumeButton = new TextButton("RESUME", skin);

        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                List<Class<? extends EntitySystem>> list = new ArrayList<>();
                list.add(MovementSystem.class);
                list.add(HudSystem.class);
                list.add(PlayerControlSystem.class);
                list.add(MonologueSystem.class);
                pauseCallback.setSystemsEnabledAndClosePauseMenu(list);
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.INSTANCE.setStateQuit();
            }
        });
        buttonTable.add(exitButton).padRight(10f);
        buttonTable.add(resumeButton).padLeft(10f);
        buttonTable.pack();

        mainTable.add(buttonTable).center().padTop(60f);

        mainTable.setBackground(new TextureRegionDrawable(pausePanel));
        mainTable.center();

        container.setFillParent(true);
        container.add(mainTable).width(900f).height(400f);
        container.background(new TextureRegionDrawable(background));
        container.getColor().a = 0f;

        this.addActor(container);
    }
}

