package com.vb.ilt.ui.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.config.GameConfig;

public class MainCharacterSpeechStage extends MonologueStage{

    public MainCharacterSpeechStage(Viewport viewport, Batch batch, AssetManager assetManager, ExitCallback exitCallback) {
        super(viewport, batch, assetManager, exitCallback);
    }

    protected void init(){
        this.text = new Label("", skin);
        this.text.setWrap(true);

        this.exitButton = new TextButton("Let's Go!", skin);

        this.exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fadeOut();
                new Timer().scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        exitCallback.exit();
                    }
                }, GameConfig.UI_TRANSITION_DURATION + 0.1f);
            }
        });


        Label label = new Label("THE DOG", skin, "stencil");
        label.setAlignment(Align.center);

        Image mainCharacterImage = new Image(this.assetManager.get(AssetDescriptors.CLOSE_UP).findRegion(RegionNames.MAIN_CHARACTER_CLOSE_UP));

        Table textWithButton = new Table();
        textWithButton.add(mainCharacterImage).width(640f).height(400f).padBottom(20f).row();
        textWithButton.add(this.text).growX().left().row();
        textWithButton.add(this.exitButton).padTop(20f).padBottom(20f).expandX();


        ScrollPane scrollPane = new ScrollPane(textWithButton);
        scrollPane.setFadeScrollBars(false);

        mainTable.add(label).padTop(20f).padBottom(20f).row();
        mainTable.add(scrollPane).center().width(640f).height(830f);

        mainTable.setBackground(new TextureRegionDrawable(region));
        mainTable.center();

        mainTable.pack();
        this.addActor(mainTable);
    }

    public void updateText(String text){
        this.text.setText(text);
    }

}
