package com.vb.ilt.entity.components.dialog_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dialog {

    private String npctext;
    private Map<String, Answer> playerAnswers;

    public Dialog(String npctext, Map<String, Answer> playerAnswers) {
        this.npctext = npctext;
        this.playerAnswers = playerAnswers;
    }

    public int getDestinationID(String answerStr){
        return playerAnswers.get(answerStr).getDestinationId();
    }

    public int getScore(String answerStr){
        return playerAnswers.get(answerStr).getScore();
    }

    public String getNpctext() {
        return npctext;
    }

    public List<String> getPlayerAnswers() {
        return new ArrayList<>(playerAnswers.keySet());
    }
}
