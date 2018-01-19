package com.vb.ilt.systems.passive;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Queue;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.entity.NPCType;
import com.vb.ilt.entity.components.AnimationComponent;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.DimensionComponent;
import com.vb.ilt.entity.components.MovementComponent;
import com.vb.ilt.entity.components.PlayerComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.TextureComponent;
import com.vb.ilt.entity.components.ZOrderComponent;
import com.vb.ilt.entity.components.dialog_model.Conversation;
import com.vb.ilt.entity.components.hud.ControlsComponent;
import com.vb.ilt.entity.components.hud.HudComponent;
import com.vb.ilt.entity.components.npc.ConversationComponent;
import com.vb.ilt.entity.components.npc.NPCComponent;
import com.vb.ilt.entity.components.world.TiledMapComponent;
import com.vb.ilt.entity.components.world.TiledMapRendererComponent;
import com.vb.ilt.entity.components.world.WorldObjectComponent;
import com.vb.ilt.shape.ShapeUtils;

import java.util.Map;


public class EntityFactorySystem extends EntitySystem{

    private static final float BOUNDS_OFFSET_X = 0.15f;
    private static final float BOUNDS_OFFSET_Y = 0.015f;

    private static final float VISION_RANGE = 6f;

    private static final float ANIMATION_TIME_FRONT = 0.075f;
    private static final float ANIMATION_TIME_WALKING = 0.02f;

    private static final int DEFAULT_PLAYER_Z_ORDER = 1;
    private static final int DEFAULT_NPC_Z_ORDER = 2;

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
        bounds.polygon = new Polygon(ShapeUtils.createRectangle(-BOUNDS_OFFSET_X, -BOUNDS_OFFSET_Y,dimension.width / 1.5f, dimension.height / 3f));

        float [] vertices = bounds.polygon.getVertices();
        float [] newVertices = new float[vertices.length];
        for(int i = 0, j = 1; j < vertices.length; i += 2, j += 2){
            newVertices[i] = (vertices[j] + vertices[i]);
            newVertices[j] = (vertices[j] - vertices[i]) / 2f;
        }

        bounds.polygon = new Polygon(newVertices);

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        Animation<TextureRegion> playerFront = new Animation<TextureRegion>(
                ANIMATION_TIME_FRONT,
                playerAtlas.findRegions(RegionNames.PLAYER_FRONT),
                Animation.PlayMode.LOOP_PINGPONG
        );

        Animation<TextureRegion> playerUp = new Animation<TextureRegion>(
                ANIMATION_TIME_WALKING,
                playerAtlas.findRegions(RegionNames.PLAYER_UP),
                Animation.PlayMode.LOOP_PINGPONG
        );

        Animation<TextureRegion> playerDown = new Animation<TextureRegion>(
                ANIMATION_TIME_WALKING,
                playerAtlas.findRegions(RegionNames.PLAYER_DOWN),
                Animation.PlayMode.LOOP_PINGPONG
        );

        Animation<TextureRegion> playerRight = new Animation<TextureRegion>(
                ANIMATION_TIME_WALKING,
                playerAtlas.findRegions(RegionNames.PLAYER_RIGHT),
                Animation.PlayMode.LOOP_PINGPONG
        );

        Animation<TextureRegion> playerLeft = new Animation<TextureRegion>(
                ANIMATION_TIME_WALKING,
                playerAtlas.findRegions(RegionNames.PLAYER_LEFT),
                Animation.PlayMode.LOOP_PINGPONG
        );

        Array<Animation<TextureRegion>> anims = new Array<Animation<TextureRegion>>();
        anims.add(playerFront);
        anims.add(playerUp);
        anims.add(playerDown);
        anims.add(playerRight);
        anims.add(playerLeft);

        animation.animations = new ImmutableArray<Animation<TextureRegion>>(anims);
        animation.setAnimationIndex(0);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = anims.get(0).getKeyFrame(0);
        log.debug(texture.region.toString());

        PlayerComponent player = engine.createComponent(PlayerComponent.class);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = DEFAULT_PLAYER_Z_ORDER;

        addEntity(position, dimension, bounds, movement, player, texture, animation, zOrder);
    }

    public void createNPCs(Map<Vector2, String> spawnPoints){
        TextureAtlas npcAtlas = assetManager.get(AssetDescriptors.NPC);

        for(Map.Entry<Vector2, String> point : spawnPoints.entrySet()) {
            String typeStr = point.getValue();

            NPCComponent npc = engine.createComponent(NPCComponent.class);
            npc.type = NPCType.valueOf(typeStr.toUpperCase());

            DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
            dimension.width = GameConfig.PLAYER_WIDTH;
            dimension.height = GameConfig.PLAYER_HEIGHT;

            PositionComponent position = engine.createComponent(PositionComponent.class);
            position.x = point.getKey().x;
            position.y = point.getKey().y;

            BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
            bounds.polygon = new Polygon(ShapeUtils.createRectangle(-BOUNDS_OFFSET_X, -BOUNDS_OFFSET_Y, dimension.width / 1.5f, dimension.height / 3f));

            float[] vertices = bounds.polygon.getVertices();
            float[] newVertices = new float[vertices.length];
            for (int i = 0, j = 1; j < vertices.length; i += 2, j += 2) {
                newVertices[i] = (vertices[j] + vertices[i]);
                newVertices[j] = (vertices[j] - vertices[i]) / 2f;
            }

            bounds.polygon = new Polygon(newVertices);

            AnimationComponent animation = engine.createComponent(AnimationComponent.class);
            Animation<TextureRegion> npcFront = new Animation<TextureRegion>(
                    ANIMATION_TIME_FRONT,
                    npcAtlas.findRegions(typeStr),
                    Animation.PlayMode.LOOP_PINGPONG
            );

            Array<Animation<TextureRegion>> anims = new Array<Animation<TextureRegion>>();
            anims.add(npcFront);

            animation.animations = new ImmutableArray<Animation<TextureRegion>>(anims);
            animation.setAnimationIndex(0);

            TextureComponent texture = engine.createComponent(TextureComponent.class);
            texture.region = anims.get(0).getKeyFrame(0);
            log.debug(texture.region.toString());

            ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
            zOrder.z = DEFAULT_NPC_Z_ORDER;

            addEntity(position, dimension, bounds, texture, animation, npc, zOrder);
        }
    }

    public void createMap(TiledMap tMap){
        TiledMapComponent tiledMap = engine.createComponent(TiledMapComponent.class);
        tiledMap.map = tMap;

        TiledMapRendererComponent mapRenderer = engine.createComponent(TiledMapRendererComponent.class);
        mapRenderer.mapRenderer = new IsometricTiledMapRenderer(tiledMap.map,1f / GameConfig.MAP_SCALE, batch);

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        MapProperties props = tiledMap.map.getProperties();

        float [] vertices = ShapeUtils.createRectangle(VISION_RANGE, VISION_RANGE,
                props.get("width", Integer.class) - VISION_RANGE * 2f,
                props.get("height", Integer.class) - VISION_RANGE * 2f);
        float [] newVertices = new float[vertices.length];

        for(int i = 0, j = 1; j < vertices.length; i += 2, j += 2){
            newVertices[i] = (vertices[j] + vertices[i]) / GameConfig.MAP_SCALE_MULTIPLIER;
            newVertices[j] = (vertices[j] - vertices[i]) / GameConfig.MAP_SCALE_MULTIPLIER / 2f + GameConfig.DEFAULT_Y_OFFSET;
        }

        bounds.polygon= new Polygon(newVertices);
        //bounds.polygon.setPosition(10, -10);

        addEntity(tiledMap, bounds, mapRenderer);
    }

    public void createCollisionObjects(Array<PolygonMapObject> mapObjects){
        log.debug("mapObjects size= " + mapObjects.size);
        for(PolygonMapObject object : mapObjects){
            Polygon originPolygon = object.getPolygon();
            float [] vertices = originPolygon.getVertices();
            float [] newVertices = new float[vertices.length];
            for(int i = 0, j = 1; j < vertices.length; i += 2, j += 2){
                newVertices[i] = (vertices[j] + vertices[i]) / (GameConfig.TILE_HEIGHT * GameConfig.MAP_SCALE_MULTIPLIER);
                newVertices[j] = (vertices[j] - vertices[i]) / (GameConfig.TILE_WIDTH * GameConfig.MAP_SCALE_MULTIPLIER);
            }

            BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
            bounds.polygon = new Polygon(newVertices);

            bounds.polygon.setPosition(
                    (originPolygon.getY() + originPolygon.getX()) / (GameConfig.TILE_HEIGHT * GameConfig.MAP_SCALE_MULTIPLIER),
                    (originPolygon.getY() - originPolygon.getX()) / (GameConfig.TILE_WIDTH * GameConfig.MAP_SCALE_MULTIPLIER )+ GameConfig.DEFAULT_Y_OFFSET);

            PositionComponent position = engine.createComponent(PositionComponent.class);
            position.x = bounds.polygon.getX();
            position.y = bounds.polygon.getY();

            WorldObjectComponent worldObject = engine.createComponent(WorldObjectComponent.class);

            addEntity(bounds, position, worldObject);
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

        addEntity(hud, controls, position, texture);
    }

    public void createDialogs(Queue<Conversation> conversations) {
        ConversationComponent conversation = engine.createComponent(ConversationComponent.class);
        conversation.conversations = conversations;
        addEntity(conversation);
    }

    private void addEntity(Component ... components){
        Entity entity = engine.createEntity();
        for(Component c : components){
            entity.add(c);
        }
        engine.addEntity(entity);
    }


}
