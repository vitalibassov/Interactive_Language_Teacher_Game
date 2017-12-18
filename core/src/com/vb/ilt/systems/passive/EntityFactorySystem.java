package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.components.BoundsComponent;
import com.vb.ilt.components.DimensionComponent;
import com.vb.ilt.components.MovementComponent;
import com.vb.ilt.components.PlayerComponent;
import com.vb.ilt.components.PositionComponent;
import com.vb.ilt.components.TextureComponent;
import com.vb.ilt.components.hud.ControlsComponent;
import com.vb.ilt.components.hud.HudComponent;
import com.vb.ilt.components.world.TiledMapComponent;
import com.vb.ilt.components.world.TiledMapRendererComponent;
import com.vb.ilt.components.world.WorldObjectComponent;
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

    public void createPlayer(Vector2 spawnPoint){
        TextureAtlas playerAtlas = assetManager.get(AssetDescriptors.PLAYER);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.PLAYER_WIDTH;
        dimension.height = GameConfig.PLAYER_HEIGHT;

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = spawnPoint.x;
        position.y = spawnPoint.y;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.polygon = new Polygon(ShapeUtils.createRectangle(dimension.width, dimension.height / 2f));
        bounds.polygon.setPosition(position.x, position.y);

        MovementComponent movement = engine.createComponent(MovementComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = playerAtlas.findRegion(RegionNames.PLAYER);
        log.debug(texture.region.toString());

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

    public void createMap(TiledMap tMap){
        TiledMapComponent tiledMap = engine.createComponent(TiledMapComponent.class);
        tiledMap.map = tMap;

        TiledMapRendererComponent mapRenderer = engine.createComponent(TiledMapRendererComponent.class);
        mapRenderer.mapRenderer = new IsometricTiledMapRenderer(tiledMap.map,1f / GameConfig.PIXELS_PER_CELL, batch);

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        MapProperties props = tiledMap.map.getProperties();

        float [] vertices = ShapeUtils.createRectangle(props.get("width", Integer.class) * 64, props.get("height", Integer.class) * 64);
        float [] newVertices = new float[vertices.length];
        for(int i = 0, j = 1; j < vertices.length; i += 2, j += 2){
            newVertices[i] = (vertices[j] + vertices[i]) / GameConfig.TILE_HEIGHT;
            newVertices[j] = (vertices[j] - vertices[i]) / GameConfig.TILE_WIDTH + 0.5f;
        }

        bounds.polygon= new Polygon(newVertices);

        Entity entity = engine.createEntity();
        entity.add(tiledMap);
        entity.add(bounds);
        entity.add(mapRenderer);
        engine.addEntity(entity);
    }

    public void createCollisionObjects(Array<PolygonMapObject> mapObjects){
        log.debug("mapObjects size= " + mapObjects.size);
        for(PolygonMapObject object : mapObjects){
            Polygon originPolygon = object.getPolygon();
            float [] vertices = originPolygon.getVertices();
            float [] newVertices = new float[vertices.length];
            for(int i = 0, j = 1; j < vertices.length; i += 2, j += 2){
                newVertices[i] = (vertices[j] + vertices[i]) / GameConfig.TILE_HEIGHT;
                newVertices[j] = (vertices[j] - vertices[i]) / GameConfig.TILE_WIDTH;
            }

            BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
            bounds.polygon = new Polygon(newVertices);

            bounds.polygon.setPosition(
                    (originPolygon.getY() + originPolygon.getX()) / GameConfig.TILE_HEIGHT,
                    (originPolygon.getY() - originPolygon.getX()) / GameConfig.TILE_WIDTH + 0.5f);

            PositionComponent position = engine.createComponent(PositionComponent.class);
            position.x = bounds.polygon.getX();
            position.y = bounds.polygon.getY();

            WorldObjectComponent worldObject = engine.createComponent(WorldObjectComponent.class);

            Entity entity = engine.createEntity();
            entity.add(bounds);
            entity.add(position);
            entity.add(worldObject);
            engine.addEntity(entity);
        }

    }

    public void createControls(){
        TextureAtlas hudAtlas = assetManager.get(AssetDescriptors.HUD);

        HudComponent hud = engine.createComponent(HudComponent.class);

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameConfig.CONTROLS_X;
        position.y = GameConfig.CONTROLS_Y;

        ControlsComponent controls = engine.createComponent(ControlsComponent.class);
        final float HALF_SIZE = GameConfig.CONTROLS_SIZE / 2f;
        controls.bottomLeft = new Polygon(ShapeUtils.createRectangle(
                position.x, position.y,
                HALF_SIZE, HALF_SIZE
        ));
        controls.bottomRight = new Polygon(ShapeUtils.createRectangle(
                position.x + HALF_SIZE, position.y,
                HALF_SIZE, HALF_SIZE
        ));
        controls.topLeft = new Polygon(ShapeUtils.createRectangle(
                position.x, position.y + HALF_SIZE,
                HALF_SIZE, HALF_SIZE
        ));
        controls.topRight = new Polygon(ShapeUtils.createRectangle(
                position.x + HALF_SIZE, position.y + HALF_SIZE,
                HALF_SIZE, HALF_SIZE
        ));

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = hudAtlas.findRegion(RegionNames.CONTROLS);

        Entity entity = engine.createEntity();
        entity.add(hud);
        entity.add(controls);
        entity.add(position);
        entity.add(texture);
        engine.addEntity(entity);
    }
}
