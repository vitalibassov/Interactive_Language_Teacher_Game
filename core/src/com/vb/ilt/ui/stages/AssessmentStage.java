package com.vb.ilt.ui.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.assets.AssetDescriptors;
import com.vb.ilt.assets.RegionNames;
import com.vb.ilt.common.GameManager;

public class AssessmentStage extends Stage{
    private static final Logger log = new Logger(ConversationStage.class.getName(), Logger.DEBUG);

    private final Skin skin;
    private Table mainTable;
    private Table buttonTable;

    private TextureRegion assessmentPanel;
    private TextureRegion background;
    private TextureRegion star;
    private TextureRegion emptyStar;


    public AssessmentStage(Viewport viewport, Batch batch, AssetManager assetManager) {
        super(viewport, batch);
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.assessmentPanel = assetManager.get(AssetDescriptors.PANELS).findRegion(RegionNames.ASSESSMENT_PANEL);
        this.background = assetManager.get(AssetDescriptors.PANELS).findRegion(RegionNames.PAUSE_BACKGROUND);
        this.star = assetManager.get(AssetDescriptors.UI_SKIN).getRegion(RegionNames.STAR);
        this.emptyStar = assetManager.get(AssetDescriptors.UI_SKIN).getRegion(RegionNames.EMPTY_STAR);
        init();
    }

    private void init(){

        mainTable = new Table();

        Table container = new Table();

        buttonTable = new Table();

        Button exitButton = new TextButton("QUIT", skin);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.INSTANCE.setStateQuit();
            }
        });
        buttonTable.add(exitButton);
        buttonTable.pack();


        mainTable.setBackground(new TextureRegionDrawable(assessmentPanel));
        mainTable.center();

        mainTable.pack();

        container.setFillParent(true);
        container.add(mainTable).width(720f).height(400f);
        container.background(new TextureRegionDrawable(background));

        this.addActor(container);
    }

    public void setStars(){
        final int maxScore = GameManager.INSTANCE.getMaxScore();
        final int score = GameManager.INSTANCE.getScore();

        int stars;
        int emptyStars;

        final Table starsTable = new Table();

        log.info("SCORE: " + score);
        log.info("MAX_SCORE: " + maxScore);

        if ((float)maxScore * 0.9f < score){
            stars = 5;
            emptyStars = 0;
        }else if((float)maxScore * 0.75f < score){
            stars = 4;
            emptyStars = 1;
        }else if ((float)maxScore * 0.5f < score){
            stars = 3;
            emptyStars = 2;
        }else if ((float)maxScore * 0.3f < score){
            stars = 2;
            emptyStars = 3;
        }else{
            stars = 1;
            emptyStars = 4;
        }

        log.info("STARS: " + stars);
        log.info("EMPTY_STARS: " + emptyStars);
        mainTable.add(starsTable).center().padTop(80f).row();
        mainTable.add(buttonTable).center().padTop(20f);

        final int starsFrames = 15;
        final float starsSpeed = 0.5f;

        new Timer().scheduleTask(new Timer.Task() {
        @Override
        public void run() {
            final Image image = new Image(star);
            starsTable.add(image).width(120f).height(120f).pad(0f, 5f, 0f, 5f);
            Gdx.input.vibrate(300);
            runImageTransitionEffect(image, starsFrames, starsSpeed);
        }
    }, 0f, 0.5f, stars - 1);

        new Timer().scheduleTask(new Timer.Task() {
        @Override
        public void run() {
            Image image = new Image(emptyStar);
            starsTable.add(image).width(120f).height(120f).pad(0f, 5f, 0f, 5f);
            runImageTransitionEffect(image, starsFrames, starsSpeed);
        }
    }, 0.5f * stars, 0.5f, emptyStars - 1);
}

    private void runImageTransitionEffect(final Image image, int frames, float speed){
        final float alpha = 1f / (float)(frames);
        image.getColor().a = 0f;
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                increaseImageAlpha(alpha, image);
            }
        }, 0f, speed / (float) frames, frames - 1);
    }

    private void increaseImageAlpha(float alpha, Image image){
        image.getColor().a = image.getColor().a + alpha;
        if (image.getColor().a > 1){
            image.getColor().a = 1;
        }
    }
}
