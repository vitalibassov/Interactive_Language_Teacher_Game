package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.TextureComponent;
import com.vb.ilt.entity.components.hud.HudComponent;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.util.Mappers;

public class HudRenderSystem extends IteratingSystem{

    private final Viewport hudViewport;
    private final SpriteBatch batch;

    private final GlyphLayout layout = new GlyphLayout();

    private static final Family FAMILY = Family.all(
            HudComponent.class,
            TextureComponent.class,
            PositionComponent.class
    ).get();

    public HudRenderSystem(Viewport hudViewport, SpriteBatch batch) {
        super(FAMILY);
        this.hudViewport = hudViewport;
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureComponent texture = Mappers.TEXTURE.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);

        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        draw(position.x, position.y, texture.region);

        batch.end();
    }

    private void draw(float x, float y, TextureRegion region) {
        batch.draw(region, x, y, GameConfig.CONTROLS_SIZE, GameConfig.CONTROLS_SIZE);
    }
}
