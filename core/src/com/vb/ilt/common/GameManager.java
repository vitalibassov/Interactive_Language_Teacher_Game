package com.vb.ilt.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.LinkedHashMap;

public final class GameManager {

    private String currentLevel;
    private int tempScore;
    private int score;
    private final LinkedHashMap<String, String> bigDictionary;
    private GameState gameState;

    public static final GameManager INSTANCE = new GameManager();

    private GameManager() {
        this.bigDictionary = new LinkedHashMap<>();
        this.gameState = GameState.PLAYING;
        loadDictionary();
    }

    private void loadDictionary(){
        for (JsonValue word : new JsonReader().parse(Gdx.files.internal("dictionary/dictionary.json"))){
            String eng = word.name().toLowerCase();
            String rus = word.asString().toLowerCase();
            bigDictionary.put(eng, eng + " - " + rus);
        }
    }

    public LinkedHashMap<String, String> getBigDictionary() {
        return bigDictionary;
    }

    public boolean isPlaying(){return gameState.isPlaying();}
    public boolean isPause(){return gameState.isPause();}
    public boolean isQuit(){return gameState.isQuit();}
    public boolean isFinished(){return gameState.isFinish();}

    public void setStateFinished(){gameState = GameState.FINISH;}
    public void setStatePause(){gameState = GameState.PAUSE;}
    public void setStateQuit(){gameState = GameState.QUIT;}
    public void setStatePlaying(){gameState = GameState.PLAYING;}

    public int getScore() { return score;}
    public void increaseTempScoreBy(int amount){tempScore += amount;}

    public void commitTempScoreAmount(){score += tempScore;}
    public void dropTempScore(){tempScore = 0;}

    public String getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxScore() {
        return 4490;
    }

    public void reset(){
        tempScore = 0;
        score = 0;
    }
}
