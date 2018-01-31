package com.vb.ilt.entity.components.dialog_model;

import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.NPCType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Conversation{

    private static final Logger log = new Logger(Conversation.class.getName(), Logger.DEBUG);

    //This class contains One conversation with one NPC
    private NPCType type;
    private Map<Integer, Dialog> dialogs;
    private int dialogIndex = 0;

    public Conversation(Map<Integer, Dialog> dialogs, NPCType type) {
        this.dialogs = dialogs;
        this.type = type;
    }

    //first Dialog, if answer == null
    public Dialog getNext(String answer){

        if (dialogIndex == -1){
            return null;
        }else if (answer == null){
            return dialogs.get(0);
        }

        Dialog currentDialog = dialogs.get(dialogIndex);
        this.dialogIndex = currentDialog.getDestinationID(answer);

        return dialogs.get(this.dialogIndex);
    }

    public NPCType getType() {
        return type;
    }

    public void setToStart (){
        dialogIndex = 0;
    }

    public List<String> getAllText(){
        List<String> text = new ArrayList<String>();
        for (Map.Entry<Integer, Dialog> dialogEntry : dialogs.entrySet()){
            Dialog dialog = dialogEntry.getValue();
            text.add(dialog.getNpctext());
            text.addAll(dialog.getPlayerAnswers());
        }
        return text;
    }
}
