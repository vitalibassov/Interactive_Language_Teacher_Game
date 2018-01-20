package com.vb.ilt.entity.components.stage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.systems.active.ConversationCallback;

import java.util.List;


public class ConversationTable extends Table{

    private final ConversationCallback conversationCallback;
    private Label npcText;
    private Table dialogTable = new Table();
    private TextureRegion region;

    public ConversationTable(AssetManager assetManager, TextureRegion region, ConversationCallback conversationCallback) {
        super(assetManager.get(AssetDescriptors.SKIN));
        this.conversationCallback = conversationCallback;
        this.region = region;
        init();
    }

    private void init(){
        defaults().pad(20);
        npcText = new Label("", getSkin());
        Table buttonTable = new Table();
        ImageButton exitButton = new ImageButton(getSkin(), ButtonStyleNames.QUIT);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                conversationCallback.exit();
            }
        });
        buttonTable.add(exitButton).right().top().expandY().expandX();

        this.dialogTable = new Table();

        ScrollPane scrollPane = new ScrollPane(dialogTable);
        scrollPane.setFadeScrollBars(false);

        add(buttonTable).grow().center().row();
        add(scrollPane).bottom().right().width(GameConfig.HUD_WIDTH - 400).height(GameConfig.HUD_HEIGHT / 2f).padBottom(50);
        this.setBackground(new TextureRegionDrawable(region));
        this.center();
        this.setFillParent(true);
        this.pack();
    }

    public void updateDialog(String text){
        npcText.setText(text);
    }

    public void setAnswers(List<String> answers){
        this.dialogTable.clear();
        this.dialogTable.add(this.npcText).grow().row();
        for (String answer : answers){
            final TextButton answBtn = new TextButton(answer, getSkin());
            answBtn.getLabelCell().padLeft(35f);
            answBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    conversationCallback.nextDialog(answBtn.getLabel().getText().toString());
                }
            });
            answBtn.getLabel().setWrap(true);
            answBtn.getLabel().setAlignment(Align.left);
            this.dialogTable.add(answBtn).pad(20).height(50).maxHeight(200).grow().row();
        }
        this.dialogTable.pack();
    }
}
