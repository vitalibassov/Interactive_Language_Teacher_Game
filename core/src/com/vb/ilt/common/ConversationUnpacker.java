package com.vb.ilt.common;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Queue;
import com.vb.ilt.entity.CharacterType;
import com.vb.ilt.entity.components.dialog_model.Answer;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.dialog_model.Dialog;

import java.util.HashMap;
import java.util.Map;

public class ConversationUnpacker extends JsonReader{

    private final FileHandle json;

    public ConversationUnpacker(FileHandle json) {
        this.json = json;
    }

    public Queue<Conversation> getConversations(){
        JsonValue root = this.parse(json);
        Queue<Conversation> conversations = new Queue<>();
        for (JsonValue nextConversation : root.iterator()){
            for (JsonValue npc : nextConversation){
                CharacterType type = CharacterType.valueOf(npc.name().toUpperCase());
                Map<Integer, Dialog> dialogs = new HashMap<>();
                for (JsonValue npcDialog : npc){
                    String npctext = npcDialog.getString("text");
                    Map<String, Answer> answers = new HashMap<>();
                    for (JsonValue answer : npcDialog.get("answers")){
                        answers.put(answer.getString("text"), new Answer(
                                answer.getString("text"),
                                answer.getInt("destinationID"),
                                answer.getInt("score")
                        ));
                    }
                    Dialog dialog = new Dialog(npctext, answers);
                    dialogs.put(Integer.valueOf(npcDialog.name()), dialog);
                }
                conversations.addLast(new Conversation(dialogs, type));
            }
        }
        return conversations;
    }
}
