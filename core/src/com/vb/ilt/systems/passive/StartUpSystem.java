package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Queue;
import com.vb.ilt.common.ConversationUnpacker;
import com.vb.ilt.common.TiledMapLayersProvider;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.dialog_model.Dialog;


public class StartUpSystem extends EntitySystem{

    private EntityFactorySystem factory;
    private final OrthographicCamera camera;

    public StartUpSystem(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean checkProcessing() {
        return false;
    }

    @Override
    public void addedToEngine(Engine engine) {
        factory = engine.getSystem(EntityFactorySystem.class);
        startUp();
    }

    private void startUp(){
        //TiledMapLayersProvider provider = new TiledMapLayersProvider("maps/main_map.tmx");
        TiledMapLayersProvider provider = new TiledMapLayersProvider("maps/test isometric map/Testing_cart.tmx");
        ConversationUnpacker unpacker = new ConversationUnpacker(Gdx.files.internal("conversations/level1.json"));
        factory.createMap(provider.getMap());
        factory.createNPCs(provider.getNpcSpawnPoints());
        factory.createDialogs(unpacker.getConversations());
        factory.createPlayer(provider.getPlayerSpawnPoint());
        factory.createCollisionObjects(provider.getPolygons());
        factory.createControls();

//        Queue<Conversation> conversations = new ConversationUnpacker(Gdx.files.internal("conversations/level1.json")).getConversations();
//        Conversation conv = conversations.removeFirst();
//        System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
//        System.out.println(conv.getType());
//        Dialog d = conv.getNext(null);
//        System.out.println(d.getNpctext());
//        for (String s : d.getPlayerAnswers()){
//            System.out.println(s);
//        }
//        d = conv.getNext("[MERCHANT] Answer 2");
//        System.out.println(d.getNpctext());
//        for (String s : d.getPlayerAnswers()){
//            System.out.println(s);
//        }
//        d = conv.getNext("[MERCHANT] Answer 3 in Index 2");
//        System.out.println(d.getNpctext());
//        for (String s : d.getPlayerAnswers()){
//            System.out.println(s);
//        }
//        d = conv.getNext("[MERCHANT] Okay GoodBye");
//        System.out.println(d);
//        System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
    }
}
