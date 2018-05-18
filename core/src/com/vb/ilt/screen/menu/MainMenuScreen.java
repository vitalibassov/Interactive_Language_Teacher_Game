package com.vb.ilt.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.common.GameManager;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.GameBase;
import com.vb.ilt.screen.ScreenBaseAdapter;
import com.vb.ilt.screen.game.GameScreen;
import com.vb.ilt.screen.transition.transitions.ScreenTransitions;
import com.vb.ilt.util.GdxUtils;

public class MainMenuScreen extends ScreenBaseAdapter{
    private final GameBase game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;
    private TextureAtlas gamePlayAtlas;

    public MainMenuScreen(GameBase game){
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {

        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());
        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gamePlayAtlas = assetManager.get(AssetDescriptors.PANELS);

        stage.addActor(createUi());
        Gdx.input.setCatchBackKey(false);
        //Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Table createUi(){
        Table table = new Table(skin);
        table.defaults().pad(10);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        //Image titleImage = new Image(skin, RegionNames.TITLE);

        Button playButton = new TextButton("PLAY", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        Button quitButton = new TextButton("QUIT", skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });

        //table.add(titleImage).row();
        table.add(playButton).row();
        table.add(quitButton);

        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void play(){
        final String level = "level_1";
        GameManager.INSTANCE.setStatePlaying();
        GameManager.INSTANCE.reset();
        GameManager.INSTANCE.setMaxScore("level_1");
        game.setScreen(new GameScreen(game, level), ScreenTransitions.SLIDE_TO_GAME_SCREEN);
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    private void quit(){
        Gdx.app.exit();
    }
}
