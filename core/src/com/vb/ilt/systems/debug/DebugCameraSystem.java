package com.vb.ilt.systems.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.vb.ilt.systems.active.CameraFollowingPlayerSystem;
import com.vb.ilt.util.debug.DebugCameraController;

public class DebugCameraSystem extends EntitySystem {

    private final DebugCameraController debugCameraController = new DebugCameraController();
    private boolean debugCamera = false;

    private final OrthographicCamera camera;

    public DebugCameraSystem(OrthographicCamera camera){
        this.camera = camera;

    }

    @Override
    public void update(float deltaTime) {
        if (debugCamera){
            debugCameraController.handleDebugInput(deltaTime);
            debugCameraController.applyTo(this.camera);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)){
            if (debugCamera){
                getEngine().getSystem(CameraFollowingPlayerSystem.class).setProcessing(true);
                this.camera.zoom = 1f;
                debugCamera = false;
            }else{
                getEngine().getSystem(CameraFollowingPlayerSystem.class).setProcessing(false);
                debugCameraController.setStartPosition(this.camera.position.x, this.camera.position.y);
                debugCameraController.setZoom(1f);
                debugCamera = true;
            }
        }
    }
}
