package com.vb.ilt.ui.tables;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Map;


public class DictionaryTable extends Table{

    private final MyWordsTabTable myWords;
    private final AllWordsTabTable allWords;
    private Skin skin;

    public DictionaryTable(Skin skin) {
        this.skin = skin;
        this.myWords = new MyWordsTabTable(this.skin);
        this.allWords = new AllWordsTabTable(this.skin, this.myWords.getAvailableWords());
        init();
    }

    private void init(){
        final WidgetGroup tabContainer = new WidgetGroup();

        final TextButton allWordsTab = new TextButton("All Words", skin);
        final TextButton myWordsTab = new TextButton("My Words", skin);

        allWordsTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tabContainer.removeActor(myWords);
                tabContainer.addActor(allWords);
                allWords.updateWords();
                switchButtonsState(allWordsTab, myWordsTab, true, false);
            }
        });

        myWordsTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tabContainer.removeActor(allWords);
                tabContainer.addActor(myWords);
                myWords.updateWords();
                switchButtonsState(myWordsTab, allWordsTab, true, false);
            }
        });

        tabContainer.removeActor(myWords);
        tabContainer.addActor(allWords);
        allWords.updateWords();
        switchButtonsState(allWordsTab, myWordsTab, true, false);

        //this.allWords.setSize(800, 600);
        this.setBackground(this.skin.getDrawable("panel"));
        this.add(allWordsTab).growX().padTop(50).padLeft(30).padRight(30);
        this.add(myWordsTab).growX().padTop(50).padLeft(30).padRight(30);
        this.row();

        this.add(tabContainer).grow().colspan(2);

        this.pack();
    }

    private void switchButtonsState(TextButton btn1, TextButton btn2, boolean b1, boolean b2){
        btn1.setDisabled(b1);
        btn2.setDisabled(b2);
    }

    public Map<String, String> getAvailableWords(){
        return allWords.getAvailableWords();
    }

    public void updateWords(){
        allWords.updateWords();
    }
}
