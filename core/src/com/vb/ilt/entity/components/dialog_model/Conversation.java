package com.vb.ilt.entity.components.dialog_model;

import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.CharacterType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Conversation{

    private static final Logger log = new Logger(Conversation.class.getName(), Logger.DEBUG);

    //This class contains One conversation with one NPC
    private CharacterType type;
    private Map<Integer, Dialog> dialogs;
    private int dialogIndex = 0;

    public Conversation(Map<Integer, Dialog> dialogs, CharacterType type) {
        this.dialogs = dialogs;
        this.type = type;
    }

    public int getScore(Dialog dialog, String answer){
        return dialog.getScore(answer);
    }

    //first Dialog, if answer == null
    public Dialog getNext(String answer){

        if (answer == null){
            return dialogs.get(0);
        }

        Dialog currentDialog = dialogs.get(dialogIndex);
        this.dialogIndex = currentDialog.getDestinationID(answer);

        return dialogs.get(this.dialogIndex);
    }

    public NonConversationalAction checkForNonConversationalAction(){
        if (this.dialogIndex < 0){
            if (this.dialogIndex == NonConversationalAction.QUIT_CONVERSATION.getValue()){
                return NonConversationalAction.QUIT_CONVERSATION;
            }else if (this.dialogIndex == NonConversationalAction.FINISH_CONVERSATION.getValue()){
                return NonConversationalAction.FINISH_CONVERSATION;
            }
        }
        return NonConversationalAction.NONE;
    }

    public Dialog getCurrentDialog(){
        return dialogs.get(dialogIndex);
    }

    public CharacterType getType() {
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

    public enum NonConversationalAction{
        NONE(0),
        QUIT_CONVERSATION(-1),
        FINISH_CONVERSATION(-2);

        private int value;

        public int getValue(){return value;}

        NonConversationalAction(int value){this.value = value;}

        public boolean isNone(){return this == NONE;}
        public boolean isQuitConversation(){return this == QUIT_CONVERSATION;}
        public boolean isFinishConversation(){return this == FINISH_CONVERSATION;}

    }
}
