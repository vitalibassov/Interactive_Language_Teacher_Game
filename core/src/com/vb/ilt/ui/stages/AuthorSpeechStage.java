package com.vb.ilt.ui.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.config.GameConfig;

public class AuthorSpeechStage extends Stage {

    private static final Logger log = new Logger(ConversationStage.class.getName(), Logger.DEBUG);

    private final Skin skin;
    private Label authorText;
    private ExitCallback exitCallback;

    private TextureRegion region;


    public AuthorSpeechStage(Viewport viewport, SpriteBatch batch, Skin skin, TextureRegion region, ExitCallback exitCallback) {
        super(viewport, batch);
        this.skin = skin;
        this.region = region;
        this.exitCallback = exitCallback;
        init();
    }

    private void init(){
        Table mainTable = new Table();
        mainTable.defaults().pad(20);
        this.authorText = new Label("", skin);
        this.authorText.setWrap(true);

        //this.npcText.setFontScale(2);

        Table buttonTable = new Table();

        Table container = new Table();

        ImageButton exitButton = new ImageButton(skin, ButtonStyleNames.QUIT);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exitCallback.exit();
            }
        });


        buttonTable.add(exitButton).right().top().expandY();

        ScrollPane scrollPane = new ScrollPane(authorText);
        scrollPane.setFadeScrollBars(false);


        mainTable.add(buttonTable).expandX().expandY().right().top().row();
        mainTable.add(scrollPane).bottom().right().width(GameConfig.HUD_WIDTH - 400).height(GameConfig.HUD_HEIGHT / 2f).padBottom(50);

        mainTable.setBackground(new TextureRegionDrawable(region));
        mainTable.center();
        mainTable.setFillParent(true);
        mainTable.pack();

        container.setFillParent(true);
        container.defaults().pad(20);

        container.pack();

        this.addActor(mainTable);
        this.addActor(container);
    }

    public void updateText(String text){
        this.authorText.setText(text);
    }
}
