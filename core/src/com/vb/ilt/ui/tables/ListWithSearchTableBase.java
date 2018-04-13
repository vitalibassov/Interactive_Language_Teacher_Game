package com.vb.ilt.ui.tables;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.LinkedHashMap;
import java.util.Map;


public abstract class ListWithSearchTableBase extends Table implements TextField.TextFieldListener{

    private final String btnStyle;
    private final Map<String, String> availableWords;
    private TextField search;
    private Table words;

    public ListWithSearchTableBase(Skin skin, String btnStyle) {
        super(skin);
        this.btnStyle = btnStyle;
        this.availableWords = new LinkedHashMap<String, String>();
        init();
    }

    public ListWithSearchTableBase(Skin skin, String btnStyle, Map<String, String> availableWords) {
        super(skin);
        this.btnStyle = btnStyle;
        this.availableWords = availableWords;
        init();
    }

    private void init(){
        this.search = new TextField("", getSkin());
        this.search.setTextFieldListener(this);

        this.words = new Table();
        for (Map.Entry<String, String> wordEntry : availableWords.entrySet()){
            addRowToWords(wordEntry.getKey(), wordEntry.getValue(), words);
        }

        words.pack();
        //words.debug();
        words.top();

        ScrollPane scrollPane = new ScrollPane(words);
        scrollPane.setFadeScrollBars(false);
        scrollPane.pack();

        add(this.search).growX().pad(40).row();
        add(scrollPane).grow().padBottom(40).padLeft(20).padRight(20);
        setFillParent(true);

//        add(search).width(800).height(50).pad(40).row();
//        add(scrollPane).width(800).height(800).padBottom(40);
//
//        setSize(800, 800);
        pack();
    }

    protected void addRowToWords(final String wordKey, final String wordValue, Table words){
        Label label = new Label(wordValue, getSkin());
        label.setWrap(true);
        label.setAlignment(Align.left);
        words.add(label).padLeft(15).padRight(15).padTop(20).padBottom(20).left().top().growX();
        if (checkWordKey(wordKey)) {
            Button btn = new ImageButton(getSkin(), this.btnStyle);
            btn.setName(wordValue);
            btn.setSize(30f, 30f);
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    processBtn(wordKey, wordValue);
                }
            });
            words.add(btn).row();
        }
        words.add().row();
    }

    protected boolean checkWordKey(final String wordKey){
        return wordKey != null;
    }

    @Override
    public void keyTyped(TextField textField, char c) {
        updateWords();
    }

    public void updateWords(){
        words.clear();
        String text = this.search.getText();
        for (Map.Entry<String, String> wordEntry : availableWords.entrySet()){
            if (wordEntry.getValue().contains(text)){
                System.out.println(wordEntry.getKey() + " : " + wordEntry.getValue());
                addRowToWords(wordEntry.getKey(), wordEntry.getValue(), this.words);
            }
        }
        if (!this.words.hasChildren()){
            addRowToWords(null,"No results...", this.words);
        }
    }

    public Map<String, String> getAvailableWords() {
        return availableWords;
    }

    public void setAvailableWords(Map<String, String> availableWords) {
        this.availableWords.clear();
        this.availableWords.putAll(availableWords);
    }

    abstract void processBtn (String wordKey, String wordValue);
}
