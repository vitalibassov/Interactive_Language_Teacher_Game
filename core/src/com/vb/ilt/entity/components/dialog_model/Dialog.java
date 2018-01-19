package com.vb.ilt.entity.components.dialog_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dialog {

    private String npctext;
    private Map<String, Integer> playerAnswers;

    public Dialog(String npctext, Map<String, Integer> playerAnswers) {
        this.npctext = npctext;
        this.playerAnswers = playerAnswers;
    }

    public int getDestinationID(String answer){
        return playerAnswers.get(answer);
    }

    public String getNpctext() {
        return npctext;
    }

    public List<String> getPlayerAnswers() {
        return new ArrayList<String>(playerAnswers.keySet());
    }
}
