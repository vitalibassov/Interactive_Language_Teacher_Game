package com.vb.ilt.entity.components.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.systems.active.DialogCallback;


public class DialogTable extends Table{

    private final DialogCallback dialogCallback;

    public DialogTable(Skin skin, DialogCallback dialogCallback) {
        super(skin);
        this.dialogCallback = dialogCallback;
        init();
    }

    private void init(){
        defaults().pad(20);

        Table buttonTable = new Table();
        ImageButton exitButton = new ImageButton(getSkin(), ButtonStyleNames.QUIT);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialogCallback.exit();
            }
        });
        buttonTable.add(exitButton).right().top().expandY().expandX();
        Label label = new Label("LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj" +
                "LOREM IMPSUM KJSHFKJAFHDSKJFHSDKJFHKJSDFKJSDFJKHSDJFJKSDHJKFHKDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS askdjaskjdhaskj", getSkin());
        label.setAlignment(Align.left);
        label.setWrap(true);

        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.setFadeScrollBars(false);

        add(buttonTable).grow().center().row();
        add(scrollPane).bottom().right().width(GameConfig.HUD_WIDTH - 400).height(GameConfig.HUD_HEIGHT / 2f).padBottom(50);
        center();
        this.setFillParent(true);
        pack();
    }
}
