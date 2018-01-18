package com.vb.ilt.entity.components.dialog_model;

import java.util.Map;

public class Conversation{

    //This class contains One conversation with one NPC

    private Map<Integer, Dialog> dialogs;
    private int dialogIndex = 0;

    public Conversation(Map<Integer, Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public Dialog getNext(String answer){
        Dialog currentDialog = dialogs.get(dialogIndex);
        this.dialogIndex = currentDialog.getDestinationID(answer);
        if (dialogIndex == -1){
            return null;
        }
        return dialogs.get(this.dialogIndex);
    }
}
