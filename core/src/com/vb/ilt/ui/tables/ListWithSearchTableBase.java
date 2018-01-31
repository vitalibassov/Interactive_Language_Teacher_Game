package com.vb.ilt.ui.tables;

import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private final LinkedHashMap<String, String> availableWords = new LinkedHashMap<String, String>();
    private Table words;

    public ListWithSearchTableBase(Skin skin, String btnStyle) {
        super(skin);
        this.btnStyle = btnStyle;
        init();
    }

    private void init(){
        TextField search = new TextField("", getSkin());
        search.setTextFieldListener(this);

        this.words = new Table();
        for (Map.Entry<String, String> wordEntry : availableWords.entrySet()){
            addRowToWords(wordEntry.getValue(), words);
        }

        words.pack();
        //words.debug();
        words.top();

        ScrollPane scrollPane = new ScrollPane(words);
        scrollPane.setFadeScrollBars(false);
        scrollPane.pack();

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
        words.add(label).padLeft(15).padRight(15).padTop(20).padBottom(20).left().top().growX();
        ImageButton btn = new ImageButton(getSkin(), this.btnStyle);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                processBtn();
            }
        });
        words.add(btn).row();
    }

    @Override
    public void keyTyped(TextField textField, char c) {
        words.clear();
        String text = textField.getText();
        for (Map.Entry<String, String> wordEntry : availableWords.entrySet()){
            if (wordEntry.getValue().contains(text)){
                addRowToWords(wordEntry.getValue(), this.words);
            }
        }
        if (!this.words.hasChildren()){
            addRowToWords("No results...", this.words);
        }
    }

    public void updateWords(){
        words.clear();
        for (Map.Entry<String, String> wordEntry : availableWords.entrySet()){
            addRowToWords(wordEntry.getValue(), this.words);
        }
    }

    public Map<String, String> getAvailableWords() {
        return availableWords;
    }

    abstract void processBtn ();
}
