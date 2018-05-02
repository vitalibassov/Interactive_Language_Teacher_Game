package com.vb.ilt.ui.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.ButtonStyleNames;

public class AuthorSpeechStage extends MonologueStage {

    public AuthorSpeechStage(Viewport viewport, Batch batch, AssetManager assetManager, ExitCallback exitCallback) {
        super(viewport, batch, assetManager, exitCallback);
    }


    protected void init(){
        Table mainTable = new Table();
        this.text = new Label("", skin);
        this.text.setWrap(true);

        Table container = new Table();
        final Table buttonTable = new Table();
        this.exitButton = new ImageButton(skin, ButtonStyleNames.QUIT);

        this.exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exitCallback.exit();
            }
        });

        Label label = new Label("AUTHOR SPEECH", skin, "stencil");

        buttonTable.add( this.exitButton).padTop(40f).right().top();
        ScrollPane scrollPane = new ScrollPane(this.text);
        scrollPane.setFadeScrollBars(false);

        mainTable.add(label);
        mainTable.add(buttonTable).right().top().row();
        mainTable.add(scrollPane).center().colspan(2).width(640f).height(780f).padBottom(40f);

        mainTable.setBackground(new TextureRegionDrawable(region));
        mainTable.center();
        mainTable.setSize(720f, 1000f);

        mainTable.pack();

        container.setFillParent(true);
        container.add(mainTable);

        this.addActor(container);
    }

    public void updateText(String text){
        this.text.setText(text);
    }
}
