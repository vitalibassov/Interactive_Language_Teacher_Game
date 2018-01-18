package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.entity.NPCType;


public class DialogSystem extends EntitySystem implements DialogCallback{

    private final AssetManager assetManager;

    private Stage stage;
    private NPCType npcType;
    private TextureRegion region;
    private final Viewport hudViewport;
    private final SpriteBatch batch;

    private HudRenderSystem hudRenderSystem;
    private PlayerControlSystem playerControlSystem;
    private MovementSystem movementSystem;

    public DialogSystem(AssetManager assetManager, Viewport hudViewport, SpriteBatch batch) {
        this.assetManager = assetManager;
        this.hudViewport = hudViewport;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        hudRenderSystem = engine.getSystem(HudRenderSystem.class);
        playerControlSystem = engine.getSystem(PlayerControlSystem.class);
        movementSystem = engine.getSystem(MovementSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        hudViewport.apply();
        stage.getBatch().setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();
        batch.draw(region, 0, 0, GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void setProcessing(boolean processing) {
        if (stage != null && npcType != null && processing) {
            super.setProcessing(true);
            this.hudRenderSystem.setProcessing(false);
            this.movementSystem.setProcessing(false);
            this.playerControlSystem.setProcessing(false);
        } else {
            super.setProcessing(false);
        }
    }

    public void setStageAndNpcType (Table table, NPCType npcType){
        TextureAtlas atlas = assetManager.get(AssetDescriptors.DIALOGS);
        this.stage = new Stage(hudViewport, batch);
        this.stage.addActor(table);
        this.npcType = npcType;
        Gdx.input.setInputProcessor(stage);
        this.region = atlas.findRegion(npcType.name().toLowerCase());
    }

    @Override
    public void exit() {
        this.setProcessing(false);
        this.hudRenderSystem.setProcessing(true);
        this.movementSystem.setProcessing(true);
        this. playerControlSystem.setProcessing(true);
        this.stage.dispose();
    }
}
