package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.entity.components.DimensionComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.TextureComponent;
import com.vb.ilt.entity.components.hud.HudComponent;
import com.vb.ilt.entity.components.hud.StageComponent;
import com.vb.ilt.util.Mappers;

public class HudSystem extends EntitySystem{

    private final Viewport hudViewport;
    private final SpriteBatch batch;

    private final GlyphLayout layout = new GlyphLayout();

    private static final Family CONTROLS_FAMILY = Family.all(
            HudComponent.class,
            TextureComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();

    private static final Family HUD_FAMILY = Family.all(
            HudComponent.class,
            StageComponent.class
    ).get();

    public HudSystem(Viewport hudViewport, SpriteBatch batch) {
        this.hudViewport = hudViewport;
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        renderControls();
        processHud();
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
}
