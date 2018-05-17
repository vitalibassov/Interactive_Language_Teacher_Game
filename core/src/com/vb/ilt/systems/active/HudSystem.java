package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.entity.components.DimensionComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.SoundComponent;
import com.vb.ilt.entity.components.TextureComponent;
import com.vb.ilt.entity.components.hud.ControlsComponent;
import com.vb.ilt.entity.components.hud.HudComponent;
import com.vb.ilt.entity.components.hud.StageComponent;
import com.vb.ilt.systems.passive.PauseSystem;
import com.vb.ilt.ui.stages.HudStage;
import com.vb.ilt.ui.stages.PauseCallback;
import com.vb.ilt.util.Mappers;

import java.util.List;

public class HudSystem extends EntitySystem implements PauseCallback{

    private static final Logger log = new Logger(HudSystem.class.getName(), Logger.DEBUG);

    private final Viewport hudViewport;
    private final Batch batch;

    private static final Family CONTROLS_FAMILY = Family.all(
            HudComponent.class,
            TextureComponent.class,
            PositionComponent.class,
            DimensionComponent.class,
            ControlsComponent.class
    ).get();

    private static final Family HUD_FAMILY = Family.all(
            HudComponent.class,
            StageComponent.class,
            SoundComponent.class
    ).get();

    public HudSystem(Viewport hudViewport, Batch batch) {
        this.hudViewport = hudViewport;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        Entity hud = engine.getEntitiesFor(HUD_FAMILY).first();
        ((HudStage)Mappers.STAGE.get(hud).stage).setPauseCallback(this);
    }

    @Override
    public void update(float deltaTime) {
        renderControls();
        processHud();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            Entity hud = getEngine().getEntitiesFor(HUD_FAMILY).first();
            StageComponent stage = Mappers.STAGE.get(hud);
            ((HudStage)stage.stage).pausePressed();
        }
    }

    private void processHud() {
        Entity hud = getEngine().getEntitiesFor(HUD_FAMILY).first();
        StageComponent stage = Mappers.STAGE.get(hud);
        stage.stage.act();
        stage.stage.draw();
        Gdx.input.setInputProcessor(stage.stage);
    }

    private void renderControls() {
        Entity controls = getEngine().getEntitiesFor(CONTROLS_FAMILY).first();
        TextureComponent texture = Mappers.TEXTURE.get(controls);
        PositionComponent position = Mappers.POSITION.get(controls);
        DimensionComponent dimension = Mappers.DIMENSION.get(controls);

        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        batch.draw(texture.region, position.x, position.y, dimension.width, dimension.height);

        batch.end();
    }

    @Override
    public void setSystemsDisabledAndShowPauseMenu(final List<Class<? extends EntitySystem>> systems) {
        PauseSystem pauseSystem = getEngine().getSystem(PauseSystem.class);
        pauseSystem.setProcessing(true);
        pauseSystem.smoothlyAppear(GameConfig.UI_TRANSITION_DURATION);
        toggleSystems(false, systems);
    }

    @Override
    public void setSystemsEnabledAndClosePauseMenu(final List<Class<? extends EntitySystem>> systems) {
        final PauseSystem pauseSystem = getEngine().getSystem(PauseSystem.class);
        pauseSystem.smoothlyDisappear(GameConfig.UI_TRANSITION_DURATION);
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                pauseSystem.setProcessing(false);
                toggleSystems(true, systems);
            }
        },  GameConfig.UI_TRANSITION_DURATION);
    }

    private void toggleSystems(boolean switcher, List<Class<? extends EntitySystem>> systems){
        Engine engine = getEngine();
        for (Class<? extends EntitySystem> systemClass: systems){
            if (engine.getSystem(systemClass) != null) {
                engine.getSystem(systemClass).setProcessing(switcher);
            }
        }
    }
}
