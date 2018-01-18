package com.vb.ilt.entity.components.stage;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.vb.ilt.assets.ButtonStyleNames;
import com.vb.ilt.config.GameConfig;


public class DialogTable extends Table{

    public DialogTable(Skin skin) {
        super(skin);
        init();
    }

    private void init(){
        defaults().pad(20);

        Table buttonTable = new Table();
        ImageButton playButton = new ImageButton(getSkin(), ButtonStyleNames.QUIT);
        buttonTable.add(playButton).right().top().expandY().expandX();
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

        add(buttonTable).grow().center().row();
        add(scrollPane).bottom().width(1000).height(GameConfig.HUD_HEIGHT / 2f);
        center();
        this.setFillParent(true);
        pack();
    }
}
