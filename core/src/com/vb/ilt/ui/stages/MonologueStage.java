package com.vb.ilt.ui.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.config.GameConfig;

public abstract class MonologueStage extends Stage {
    final Skin skin;
    Label text;
    ExitCallback exitCallback;
    final AssetManager assetManager;
    Button exitButton;
    private final float APPEARANCE_DELAY = 3f;
    final Table mainTable;

    protected TextureRegion region;

    MonologueStage(Viewport viewport, Batch batch, AssetManager assetManager, ExitCallback exitCallback) {
        super(viewport, batch);
        this.assetManager = assetManager;
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.region = assetManager.get(AssetDescriptors.PANELS).findRegion(RegionNames.MONOLOGUE_SPEECH);
        this.exitCallback = exitCallback;
        this.mainTable = new Table();
        this.mainTable.getColor().a = 0f;
        this.mainTable.setSize(720f, 1000f);
        this.mainTable.setPosition(GameConfig.HUD_WIDTH_CENTER - mainTable.getWidth() / 2f, GameConfig.HUD_HEIGHT - mainTable.getHeight() / 2f);

        init();
        postponeButtonAppearance(this.exitButton, this.APPEARANCE_DELAY);
    }

    protected abstract void init();
    public abstract void updateText(String text);

    public void postponeButtonAppearance(){
        postponeButtonAppearance(this.exitButton, this.APPEARANCE_DELAY);
    }

    private void postponeButtonAppearance(final Button button, float delay){
        button.setDisabled(true);
        button.setVisible(false);
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                button.setDisabled(false);
                button.setVisible(true);
            }
        }, delay);
    }

    public void fadeIn(){
        this.mainTable.addAction(Actions.alpha(1f, GameConfig.UI_TRANSITION_DURATION));
        this.mainTable.addAction(Actions.moveTo(GameConfig.HUD_WIDTH_CENTER - mainTable.getWidth() / 2f, GameConfig.HUD_HEIGHT_CENTER - mainTable.getHeight() / 2f, GameConfig.UI_TRANSITION_DURATION));
    }

    public void fadeOut(){
        this.mainTable.addAction(Actions.alpha(0f, GameConfig.UI_TRANSITION_DURATION));
        this.mainTable.addAction(Actions.moveTo(GameConfig.HUD_WIDTH_CENTER - mainTable.getWidth() / 2f, GameConfig.HUD_HEIGHT, GameConfig.UI_TRANSITION_DURATION));
    }
}
