package com.vb.ilt.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;

import java.util.LinkedHashMap;

public final class GameManager {

    private static final Logger log = new Logger(GameManager.class.getName(), Logger.DEBUG);

    private int currentLevel;
    private int tempScore;
    private int score;
    private final LinkedHashMap<String, String> bigDictionary;
    private GameState gameState;

    public static final GameManager INSTANCE = new GameManager();

    private GameManager() {
        this.bigDictionary = new LinkedHashMap<String, String>();
        this.gameState = GameState.PLAYING;
        loadDictionary();
    }

    private void loadDictionary(){
        for (String word : Gdx.files.internal("dictionary/dictionary").readString().split("\n")){
            String[] engrus = word.toLowerCase().split("\t");
            if (engrus.length == 2){
                bigDictionary.put(engrus[0], engrus[0] + " - " + engrus[1]);
            }
        }
    }

    public LinkedHashMap<String, String> getBigDictionary() {
        return bigDictionary;
    }

    public boolean isPlaying(){return gameState.isPlaying();}
    public boolean isPause(){return gameState.isPause();}
    public boolean isFinished(){return gameState.isFinish();}

    public void setStateFinished(){gameState = GameState.FINISH;}
    public void setStatePause(){gameState = GameState.PAUSE;}
    public void setStatePlaying(){gameState = GameState.PLAYING;}

    public int getScore() { return score;}
    public void increaseTempScoreBy(int amount){tempScore += amount;}

    public void commitTempScoreAmount(){score += tempScore;}
    public void dropTempScore(){tempScore = 0;}

    public void reset(){
        tempScore = 0;
        score = 0;
    }
}
