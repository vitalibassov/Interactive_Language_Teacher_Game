package com.vb.ilt.common;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Queue;
import com.vb.ilt.entity.components.dialog_model.Conversation;

public class ConversationUnpacker extends JsonReader{

    private static final Logger log = new Logger(ConversationUnpacker.class.getName(), Logger.DEBUG);

    private final FileHandle json;

    public ConversationUnpacker(FileHandle json) {
        this.json = json;
    }

    public Queue<Conversation> getConversations(){
        JsonValue root = this.parse(json);
        //log.debug(root.toString());
        for (JsonValue npc : root.iterator()){
            //log.debug(npc.toString());
            String NPCName = npc.name();
            log.debug(NPCName.toUpperCase());
            for (int i = 0;;i++){
                JsonValue conversation = npc.get(i);
                if (conversation == null) break;
                log.debug(conversation.toString());
            }
        }
        //log.debug(NPCs.toString());
        return null;
    }
}
