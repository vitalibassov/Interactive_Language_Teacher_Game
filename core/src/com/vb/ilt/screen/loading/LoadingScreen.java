package com.vb.ilt.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.InteractiveLangTeacherGame;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.screen.menu.MainMenuScreen;
import com.vb.ilt.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    private static final Logger log = new Logger(LoadingScreen.class.getName(), Logger.DEBUG);

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2f;
    private static final float PROGRESS_BAR_HEIGHT = 60;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen = false;

    private final InteractiveLangTeacherGame game;
    private final AssetManager assetManager;

    private LoadingStage loadingStage;

    public LoadingScreen(InteractiveLangTeacherGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.HUD);
        assetManager.finishLoading();

        log.debug("IS NULL: " + (assetManager.get(AssetDescriptors.HUD).findRegion(RegionNames.BAR_FRAME) == null));
        log.debug("IS NULL: " + (assetManager.get(AssetDescriptors.HUD).findRegion(RegionNames.BAR) == null));
        loadingStage = new LoadingStage(viewport, game.getBatch(),
                assetManager.get(AssetDescriptors.HUD).findRegion(RegionNames.BAR_FRAME),
                assetManager.get(AssetDescriptors.HUD).findRegion(RegionNames.BAR));

        log.debug("show");


        assetManager.load(AssetDescriptors.DEFAULT_FONT);
        assetManager.load(AssetDescriptors.STENCIL_FONT);

        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.PLAYER);
        assetManager.load(AssetDescriptors.NPC);
        assetManager.load(AssetDescriptors.CLOSE_UP);
        assetManager.load(AssetDescriptors.PANELS);
        assetManager.load(AssetDescriptors.STEP_SOUND);
        assetManager.load(AssetDescriptors.DOOR_SOUND);
        assetManager.load(AssetDescriptors.FINISHED_SOUND);
        assetManager.load(AssetDescriptors.ACHIEVEMENT_SOUND);
        assetManager.load(AssetDescriptors.MAIN_MUSIC);
        GameManager.INSTANCE.setStatePlaying();
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();

        draw();

        if (changeScreen) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void update(float delta) {
        progress = assetManager.getProgress();
        loadingStage.setBarWidth(progress);
        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        loadingStage.act();
        loadingStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    @Override
    public void hide() {
        log.debug("hide");
        dispose();
    }

    @Override
    public void dispose() {
        log.debug("dispose");
        renderer.dispose();
        renderer = null;
    }

    private static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
