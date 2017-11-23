package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.vb.ilt.components.PlayerComponent;
import com.vb.ilt.components.PositionComponent;

public class CameraFollowingPlayerSystem extends IteratingSystem{

    private final OrthographicCamera camera;

    private static final Family FAMILY = Family.all(
            PlayerComponent.class,
            PositionComponent.class
    ).get();

    public CameraFollowingPlayerSystem(OrthographicCamera camera) {
        super(FAMILY);
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
