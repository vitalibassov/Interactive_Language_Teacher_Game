package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.common.TiledMapLayersProvider;
import com.vb.ilt.components.BoundsComponent;
import com.vb.ilt.components.DimensionComponent;
import com.vb.ilt.components.MovementComponent;
import com.vb.ilt.components.PlayerComponent;
import com.vb.ilt.components.PositionComponent;
import com.vb.ilt.components.TextureComponent;
import com.vb.ilt.components.world.TiledMapComponent;
import com.vb.ilt.components.world.TiledMapRendererComponent;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.shape.ShapeUtils;


public class EntityFactorySystem extends EntitySystem{

    private static final Logger log = new Logger(EntityFactorySystem.class.getName(), Logger.DEBUG);

    private PooledEngine engine;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private TextureAtlas gamePlayAtlas;

    public EntityFactorySystem(AssetManager assetManager, SpriteBatch batch){
        this.batch = batch;
        this.assetManager = assetManager;
    }

    @Override
    public boolean checkProcessing() {
        return false;
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine) engine;
    }

    public void createPlayer(){
        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.PLAYER_WIDTH;
        dimension.height = GameConfig.PLAYER_HEIGHT;

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameConfig.WORLD_CENTER_X - (dimension.width / 2f);
        position.y = GameConfig.WORLD_CENTER_Y - (dimension.height / 2f);

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.polygon = new Polygon(ShapeUtils.createRectangle(position.x, position.y, dimension.width, dimension.height));
        bounds.polygon.setPosition(position.x, position.y);

        MovementComponent movement = engine.createComponent(MovementComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.texture = new Texture("player/player.png");

        PlayerComponent player = engine.createComponent(PlayerComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        entity.add(player);
        entity.add(texture);
        engine.addEntity(entity);
    }

    public void createMap(TiledMapLayersProvider provider){
        TiledMapComponent tiledMap = engine.createComponent(TiledMapComponent.class);
        tiledMap.map = provider.getMap();

        TiledMapRendererComponent mapRenderer = engine.createComponent(TiledMapRendererComponent.class);
        mapRenderer.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap.map,1f / GameConfig.PIXELS_PER_CELL, batch);

        Entity entity = engine.createEntity();
        entity.add(tiledMap);
        entity.add(mapRenderer);
        engine.addEntity(entity);
    }

    public void createCollisionObjects(TiledMapLayersProvider provider){
        Array<PolygonMapObject> mapObjects = provider.getPolygons();
        log.debug("mapObjects size= " + mapObjects.size);
        for(PolygonMapObject object : mapObjects){
            Polygon originPolygon = object.getPolygon();
            float [] vertices = originPolygon.getVertices();
            float [] newVertices = new float[vertices.length];
            for(int i = 0; i < vertices.length; i++){
                newVertices[i] = vertices[i] / GameConfig.PIXELS_PER_CELL;
            }

            BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
            bounds.polygon = new Polygon(newVertices);

            bounds.polygon.setPosition(
                    originPolygon.getX() / GameConfig.PIXELS_PER_CELL,
                    originPolygon.getY() / GameConfig.PIXELS_PER_CELL);

            PositionComponent position = engine.createComponent(PositionComponent.class);
            position.x = bounds.polygon.getX();
            position.y = bounds.polygon.getY();

            Entity entity = engine.createEntity();
            entity.add(bounds);
            entity.add(position);
            engine.addEntity(entity);
        }

    }
}
