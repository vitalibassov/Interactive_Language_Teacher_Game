package com.vb.ilt.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.InteractiveLangTeacherGame;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.systems.active.BoundsSystem;
import com.vb.ilt.systems.active.CameraFollowingPlayerSystem;
import com.vb.ilt.systems.active.MovementSystem;
import com.vb.ilt.systems.active.PlayerControlSystem;
import com.vb.ilt.systems.active.RenderWorldSystem;
import com.vb.ilt.systems.active.collision.WorldObjectsCollisionSystem;
import com.vb.ilt.systems.debug.DebugRenderSystem;
import com.vb.ilt.systems.debug.GridRenderSystem;
import com.vb.ilt.systems.passive.EntityFactorySystem;
import com.vb.ilt.systems.passive.StartUpSystem;
import com.vb.ilt.util.GdxUtils;

public class GameScreen extends ScreenAdapter{

    private final InteractiveLangTeacherGame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    //private final Skin skin;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private BitmapFont font;

    public GameScreen(InteractiveLangTeacherGame game, Skin skin) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        camera.setToOrtho(false);
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        renderer = new ShapeRenderer();
        engine = new PooledEngine();



        engine.addSystem(new EntityFactorySystem(assetManager, batch));


        engine.addSystem(new PlayerControlSystem());
        engine.addSystem(new WorldObjectsCollisionSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundsSystem());


        engine.addSystem(new CameraFollowingPlayerSystem(camera, viewport));
        engine.addSystem(new RenderWorldSystem(viewport, batch));

        engine.addSystem(new GridRenderSystem(viewport, renderer));
        //engine.addSystem(new DebugCameraSystem(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y, camera));
        engine.addSystem(new DebugRenderSystem(viewport, renderer));
        engine.addSystem(new StartUpSystem());

    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        engine.removeAllEntities();
    }
}
