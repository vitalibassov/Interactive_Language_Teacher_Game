package com.vb.ilt;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.screen.ScreenBase;
import com.vb.ilt.screen.transition.ScreenTransition;

public abstract class GameBase implements ApplicationListener{

    private AssetManager assetManager;
    private SpriteBatch batch;

    private Viewport viewport;

    private ScreenBase currentScreen;
    private ScreenBase nextScreen;

    private FrameBuffer currentFrameBuffer;
    private FrameBuffer nextFrameBuffer;

    private float time;
    private ScreenTransition transition;
    private boolean renderedToTexture;
    private boolean transitionInProgress;

    public abstract void postCreate();

    @Override
    public final void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        postCreate();
    }

    public void setScreen(ScreenBase screen){
        setScreen(screen, null);
    }

    public void setScreen(ScreenBase screen, ScreenTransition transition){
        if(transitionInProgress){
            return;
        }

        if(currentScreen == screen){
            return;
        }

//        if(nextScreen == screen){
//            return;
//        }

        this.transition = transition;

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        currentFrameBuffer = FrameBuffer.createFrameBuffer(Pixmap.Format.RGB888, width, height, true);
        nextFrameBuffer = FrameBuffer.createFrameBuffer(Pixmap.Format.RGB888, width, height, true);

        Gdx.input.setInputProcessor(null);

        nextScreen = screen;

        nextScreen.show();
        nextScreen.resize(width, height);
        nextScreen.pause();
        time = 0;

        if(currentScreen != null){
            currentScreen.pause();
        }
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if(nextScreen == null){
            if(currentScreen != null){
                currentScreen.render(delta);
            }
        }else{
            transitionInProgress = true;
            float duration = getDuration();
            time = Math.min(time + delta, duration);

            if(!renderedToTexture){
                renderScreensToTexture();
                renderedToTexture = true;
            }

            updateTransition();
        }
    }

    @Override
    public void resize(int width, int height) {
        if(currentScreen != null){
            currentScreen.resize(width, height);
        }

        if(nextScreen != null){
            nextScreen.resize(width, height);
        }

        viewport.setWorldSize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        if(currentScreen != null){
            currentScreen.pause();
        }
    }

    @Override
    public void resume() {
        if(currentScreen != null){
            currentScreen.resume();
        }
    }

    @Override
    public void dispose() {
        if(currentScreen != null){
            currentScreen.dispose();
        }

        if(nextScreen != null){
            nextScreen.dispose();
        }

        if(currentFrameBuffer != null){
            currentFrameBuffer.dispose();
        }

        if(nextFrameBuffer != null){
            nextFrameBuffer.dispose();
        }

        currentScreen = null;
        nextScreen = null;

        assetManager.dispose();
        batch.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    private float getDuration(){
        return transition == null ? 0f : transition.getDuration();
    }

    private boolean isTransitionFinished(){
        return time >= getDuration();
    }

    private void renderScreensToTexture(){
        if(currentScreen != null){
            currentFrameBuffer.begin();
            currentScreen.render(0);
            currentFrameBuffer.end();
        }

        nextFrameBuffer.begin();
        nextScreen.render(0);
        nextFrameBuffer.end();
    }

    private void updateTransition(){
        if(transition == null || isTransitionFinished()){
            if(currentScreen != null){
                currentScreen.hide();
            }

            nextScreen.resume();
            Gdx.input.setInputProcessor(nextScreen.getInputProcessor());

            currentScreen = nextScreen;
            nextScreen = null;
            transition = null;
            currentFrameBuffer.dispose();
            nextFrameBuffer.dispose();
            currentFrameBuffer = null;
            nextFrameBuffer = null;
            renderedToTexture = false;
            transitionInProgress = false;
            return;
        }

        float percentage = time / getDuration();

        Texture currentScreenTexture = currentFrameBuffer.getColorBufferTexture();
        Texture nextScreenTexture = nextFrameBuffer.getColorBufferTexture();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        transition.render(batch, currentScreenTexture, nextScreenTexture, percentage);
    }
}
