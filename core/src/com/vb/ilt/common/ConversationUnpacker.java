package com.vb.ilt.common;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Queue;
import com.vb.ilt.entity.NPCType;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.dialog_model.Dialog;

import java.util.HashMap;
import java.util.Map;

public class ConversationUnpacker extends JsonReader{

    private static final Logger log = new Logger(ConversationUnpacker.class.getName(), Logger.DEBUG);

    private final FileHandle json;

    public ConversationUnpacker(FileHandle json) {
        this.json = json;
    }

    public Queue<Conversation> getConversations(){
        JsonValue root = this.parse(json);
        Queue<Conversation> conversations = new Queue<Conversation>();
        for (JsonValue nextConversation : root.iterator()){
            for (JsonValue npc : nextConversation){
                NPCType type = NPCType.valueOf(npc.name().toUpperCase());
                Map<Integer, Dialog> dialogs = new HashMap<Integer, Dialog>();
                for (JsonValue npcDialog : npc){
                    String npctext = npcDialog.getString("text");
                    Map<String, Integer> answers = new HashMap<String, Integer>();
                    for (JsonValue answer : npcDialog.get("answers")){
                        answers.put(answer.getString("text"), answer.getInt("destinationID"));
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
