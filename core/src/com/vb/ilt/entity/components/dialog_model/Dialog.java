package com.vb.ilt.entity.components.dialog_model;

import java.util.Map;

public class Dialog {

    private Map<Integer, String> NPCText;
    private Map<String, Integer> playerAnswers;

    public Dialog(Map<Integer, String> NPCText, Map<String, Integer> playerAnswers) {
        this.NPCText = NPCText;
        this.playerAnswers = playerAnswers;
    }

    public int getDestinationID(String answer){
        return playerAnswers.get(answer);
    }
}
