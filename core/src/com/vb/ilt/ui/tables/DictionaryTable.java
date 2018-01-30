package com.vb.ilt.ui.tables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.assets.AssetDescriptors;

public class DictionaryTable extends Table implements TextField.TextFieldListener{

    private static final Logger log = new Logger(DictionaryTable.class.getName(), Logger.DEBUG);

    private final AssetManager assetManager;
    private Array<String> availableWords = new Array<String>();
    private Table words;

    public DictionaryTable(AssetManager assetManager) {
        super(assetManager.get(AssetDescriptors.SKIN));
        this.assetManager = assetManager;

        for (String word : Gdx.files.internal("dictionary/dictionary.txt").readString().split("\n")){
            availableWords.add(word);

        }

        init();
    }

    private void init(){
        TextField search = new TextField("", getSkin());
        search.setTextFieldListener(this);

        this.words = new Table();
        for (String s : availableWords){
            addRowToWords(s, words);
        }

        words.pack();
        ScrollPane scrollPane = new ScrollPane(words);
        scrollPane.setFadeScrollBars(false);

        add(search).width(500).height(50).pad(40).row();
        add(scrollPane).width(500).height(800).padBottom(40);
        
        setBackground(getSkin().getDrawable("panel"));
        setSize(500, 800);
        pack();
    }

    private void addRowToWords(String word, Table words){
        Label label = new Label(word, getSkin());
        label.setWrap(true);
        label.setAlignment(Align.left);
        words.add(label).padLeft(30).padRight(30).left().grow().row();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void keyTyped(TextField textField, char c) {
        words.clear();
        String text = textField.getText();
        for (String word : availableWords){
            if (word.contains(text)){
                addRowToWords(word, this.words);
            }
        }
        log.debug(text);
    }
}
