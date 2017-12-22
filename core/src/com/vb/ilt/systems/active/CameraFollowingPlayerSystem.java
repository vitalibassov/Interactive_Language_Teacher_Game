package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.entity.components.PlayerComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.util.Mappers;

public class CameraFollowingPlayerSystem extends IteratingSystem{

    private final OrthographicCamera camera;
    private final Viewport viewport;

    private static final Family FAMILY = Family.all(
            PlayerComponent.class,
            PositionComponent.class
    ).get();

    public CameraFollowingPlayerSystem(OrthographicCamera camera, Viewport viewport) {
        super(FAMILY);
        this.camera = camera;
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        float playerX = position.x + GameConfig.PLAYER_WIDTH / 2f;
        float playerY = position.y + GameConfig.PLAYER_HEIGHT / 2f;

        camera.position.set(playerX, playerY, 0);
        camera.update();
    }
}
