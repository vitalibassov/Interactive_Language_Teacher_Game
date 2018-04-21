package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.common.ConversationUnpacker;
import com.vb.ilt.common.TiledMapManager;
import com.vb.ilt.common.TiledMapObjectsProvider;
import com.vb.ilt.entity.components.DictionaryComponent;


public class StartUpSystem extends EntitySystem{

    private EntityFactorySystem factory;
    private final TiledMapManager mapManager;
    private final String conversationName;
    private final Viewport hudViewport;

    public StartUpSystem(Viewport hudViewport, TiledMapManager mapManager, String conversationName) {
        this.mapManager = mapManager;
        this.conversationName = conversationName;
        this.hudViewport = hudViewport;
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
        ConversationUnpacker unpacker = new ConversationUnpacker(Gdx.files.internal(this.conversationName));
        TiledMapObjectsProvider provider = this.mapManager.getMapProvider("main");
        factory.createPortalSensors(provider.getSensors());
        factory.createPortalSensorSpawns(provider.getSpawnsNearSensors());
        factory.createMap(provider.getMap());
        factory.createNPCs(provider.getNpcSpawnPoints());
        factory.createStory(unpacker.getConversations());
        factory.createPlayer(provider.getPlayerSpawnPoint());
        factory.createCollisionObjects(provider.getCollisionObjects());
        factory.createMusic(AssetDescriptors.MAIN_MUSIC);
        DictionaryComponent dict = factory.createDictionary();
        factory.createHud(hudViewport, dict);
        factory.createControls();
    }
}
