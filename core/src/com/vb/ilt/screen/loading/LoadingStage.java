package com.vb.ilt.screen.loading;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingStage extends Stage{

    private static final float MAX_BAR_WIDTH = 1105f;

    protected Label text;
    private TextureRegion barFrameRegion;
    private TextureRegion barRegion;
    private Actor bar;

    protected TextureRegion region;

    LoadingStage(Viewport viewport, Batch batch, TextureRegion barFrameRegion, TextureRegion barRegion) {
        super(viewport, batch);
        this.barRegion = barRegion;
        this.barFrameRegion = barFrameRegion;
        init();
    }

    private void init() {
        Table container = new Table();
        container.setFillParent(true);

        Table barFrame = new Table();
        barFrame.setBackground(new TextureRegionDrawable(barFrameRegion));

        this.bar = new LoadingBar(barRegion);
        this.bar.setHeight(38f);
        this.bar.setPosition(0f, 0f);

        barFrame.add(bar).width(MAX_BAR_WIDTH).padBottom(4f);
        container.add(barFrame).center();

        this.addActor(container);
    }

    public void setBarWidth(float percent){
        bar.setWidth(MAX_BAR_WIDTH * percent);
        bar.setX(((MAX_BAR_WIDTH / 2f) - bar.getWidth() / 2f) + 5f);
    }
}
