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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.systems.active.ConversationCallback;
import com.vb.ilt.ui.tables.DictionaryTable;

import java.util.List;
import java.util.Map;

public class ConversationStage extends Stage {

    private static final Logger log = new Logger(ConversationStage.class.getName(), Logger.DEBUG);

    private final ConversationCallback conversationCallback;
    private final Skin skin;
    private final DictionaryTable dictTable;
    private Label npcText;
    private Table dialogTable;

    private TextureRegion region;

    public ConversationStage(Viewport viewport, SpriteBatch batch, Skin skin, TextureRegion region, ConversationCallback conversationCallback) {
        super(viewport, batch);
        this.skin = skin;
        this.conversationCallback = conversationCallback;
        this.dictTable = new DictionaryTable(skin);
        this.region = region;
        init();
    }

    private void init(){
        Table mainTable = new Table();
        mainTable.defaults().pad(20);
        this.npcText = new Label("", skin);
        this.npcText.setWrap(true);
        Table buttonTable = new Table();

        Table container = new Table();

        this.dictTable.setVisible(false);

        ImageButton exitButton = new ImageButton(skin, ButtonStyleNames.QUIT);
        final ImageButton dictButton = new ImageButton(skin, ButtonStyleNames.DICT);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                conversationCallback.exit();
            }
        });

        dictButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dictTable.setVisible(!dictTable.isVisible());
            }
        });

        buttonTable.add(dictButton).right().top().expandY().padRight(60);
        buttonTable.add(exitButton).right().top().expandY();


        this.dialogTable = new Table();

        ScrollPane scrollPane = new ScrollPane(dialogTable);
        scrollPane.setFadeScrollBars(false);


        mainTable.add(buttonTable).expandX().expandY().right().top().row();
        mainTable.add(scrollPane).bottom().right().width(GameConfig.HUD_WIDTH - 400).height(GameConfig.HUD_HEIGHT / 2f).padBottom(50);

        mainTable.setBackground(new TextureRegionDrawable(region));
        mainTable.center();
        mainTable.setFillParent(true);
        mainTable.pack();
        mainTable.debugAll();

        container.setFillParent(true);
        container.defaults().pad(20);

        container.add(this.dictTable).width(1000).height(900).top().right().padTop(150).expandX().expandY();
        container.pack();

        this.addActor(mainTable);
        this.addActor(container);
    }

    public void updateDialog(String text){
        npcText.setText(text);
    }

    public void setAnswers(List<String> answers){
        this.dialogTable.clear();
        this.dialogTable.add(this.npcText).grow().row();
        for (String answer : answers){
            final TextButton answBtn = new TextButton(answer, skin);
            answBtn.getLabelCell().pad(35f);
            answBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    conversationCallback.nextDialog(answBtn.getLabel().getText().toString());
                }
            });
            answBtn.getLabel().setWrap(true);
            answBtn.getLabel().setAlignment(Align.left);
            this.dialogTable.add(answBtn).pad(20).grow().row();
        }
        this.dialogTable.pack();
    }

    public void setAvailableAllWords(Map<String, String> words){
        dictTable.setAvailableAllWords(words);
    }

    public void setAvailableMyWords(Map<String, String> words){
        dictTable.setAvailableMyWords(words);
    }

    public void updateWords(){
        dictTable.updateWords();
    }

    public Map<String, String> getAvailableMyWords(){
        return dictTable.getAvailableMyWords();
    }
}
