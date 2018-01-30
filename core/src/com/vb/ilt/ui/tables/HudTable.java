package com.vb.ilt.ui.tables;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.ButtonStyleNames;

public class HudTable extends Table{

    private final AssetManager assetManager;
    private final DictionaryTable dictTable;

    public HudTable(AssetManager assetManager) {
        super(assetManager.get(AssetDescriptors.SKIN));
        this.assetManager = assetManager;
        this.dictTable = new DictionaryTable(assetManager);
        init();
    }

    private void init(){
        defaults().pad(20);

        dictTable.setVisible(false);
        Table buttonTable = new Table();

        ImageButton pauseButton = new ImageButton(getSkin(), ButtonStyleNames.PAUSE);
        ImageButton dictButton = new ImageButton(getSkin(), ButtonStyleNames.DICT);

        dictButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dictTable.setVisible(!dictTable.isVisible());
            }
        });


        buttonTable.add(dictButton);
        buttonTable.add(pauseButton).padLeft(60);
        buttonTable.pack();

        add(buttonTable).top().right().expandY().expandX();
        row();
        add(dictTable).top().right().expandX().expandY();

        setFillParent(true);
        pack();
    }
}
