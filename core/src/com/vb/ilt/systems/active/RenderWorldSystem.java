package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.components.DimensionComponent;
import com.vb.ilt.components.PositionComponent;
import com.vb.ilt.components.TextureComponent;
import com.vb.ilt.components.world.TiledMapComponent;
import com.vb.ilt.components.world.TiledMapRendererComponent;
import com.vb.ilt.util.Mappers;

public class RenderWorldSystem extends EntitySystem{

    private static final Logger log = new Logger(RenderWorldSystem.class.getName(), Logger.DEBUG);

    private final SpriteBatch batch;
    private final Viewport viewport;
    private TiledMap map;

    private static final Family MAP_FAMILY = Family.all(
            TiledMapComponent.class,
            TiledMapRendererComponent.class
    ).get();

    private static final Family GAME_OBJECTS_FAMILY = Family.all(
            PositionComponent.class,
            TextureComponent.class,
            DimensionComponent.class
    ).get();


    public RenderWorldSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> tiledMaps = getEngine().getEntitiesFor(MAP_FAMILY);
        //log.debug("tiledMaps size= " + tiledMaps.size());
        //TiledMap map = Mappers.MAP.get(tiledMaps.first()).map;
        IsometricTiledMapRenderer mapRenderer = Mappers.MAP_RENDERER.get(tiledMaps.first()).mapRenderer;

        viewport.apply();
        //mapRenderer.setView(viewport.getCamera());
        batch.setProjectionMatrix(viewport.getCamera().combined);
        mapRenderer.setView((OrthographicCamera) viewport.getCamera());
        //mapRenderer.render(new int[]{0, 1});
        mapRenderer.render();
        batch.begin();

        draw();

        batch.end();
        mapRenderer.render(new int[]{2});
    }

    private void draw(){
        for (Entity entity : getEngine().getEntitiesFor(GAME_OBJECTS_FAMILY)){
            PositionComponent position = Mappers.POSITION.get(entity);
            DimensionComponent dimension = Mappers.DIMENSION.get(entity);
            TextureComponent texture = Mappers.TEXTURE.get(entity);

            batch.draw(texture.texture,
                    position.x, position.y,
                    dimension.width, dimension.height
            );
        }
    }
}
