package com.vb.ilt.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.LinkedHashMap;

public final class GameManager {

    private int maxScore;
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

    public void setMaxScore(String levelName){
        JsonValue jsonValue = new JsonReader().parse(Gdx.files.internal("props/" + levelName + ".json"));
        this.maxScore = jsonValue.getInt("max_score");
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

    public int getMaxScore() {
        return maxScore;
    }

    public void reset(){
        tempScore = 0;
        score = 0;
    }
}
