package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.entity.components.MovementComponent;
import com.vb.ilt.entity.components.PlayerComponent;
import com.vb.ilt.entity.components.hud.ControlsComponent;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.util.Mappers;

public class PlayerControlSystem extends EntitySystem{

    private final Viewport hudViewport;

    //Inappropriate behavior if the value is lower
    private static final float MIN_STOP_VELOCITY = 0.05f;

    public static final Family PLAYER = Family.all(
            PlayerComponent.class,
            MovementComponent.class
    ).get();

    public static final Family CONTROLS = Family.all(
            ControlsComponent.class
    ).get();

    public PlayerControlSystem(Viewport hudViewport) {
        this.hudViewport = hudViewport;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER);
        ImmutableArray<Entity> controls = getEngine().getEntitiesFor(CONTROLS);

        for(Entity player : players) {
            for (Entity control : controls) {
                MovementComponent movement = Mappers.MOVEMENT.get(player);
                ControlsComponent controlsComp = Mappers.CONTROLS.get(control);

                Vector2 worldTouch;
                if(Gdx.input.isTouched()){
                    Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                    worldTouch = hudViewport.unproject(screenTouch);
                }else{
                    worldTouch = new Vector2(0, 0);
                }

                movement.velocity.setZero();

                boolean up = Gdx.input.isKeyPressed(Input.Keys.UP) || controlsComp.topRight.contains(worldTouch);
                boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN) || controlsComp.bottomLeft.contains(worldTouch);
                boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controlsComp.bottomRight.contains(worldTouch);
                boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT) || controlsComp.topLeft.contains(worldTouch);

                if (up) {
                    movement.velocity.x = GameConfig.PLAYER_VELOCITY * 1f;
                    movement.velocity.y = GameConfig.PLAYER_VELOCITY * 0.5f;
                } else if (down) {
                    movement.velocity.x = -GameConfig.PLAYER_VELOCITY * 1f;
                    movement.velocity.y = -GameConfig.PLAYER_VELOCITY * 0.5f;
                } else if (right) {
                    movement.velocity.x = GameConfig.PLAYER_VELOCITY * 1f;
                    movement.velocity.y = -GameConfig.PLAYER_VELOCITY * 0.5f;
                } else if (left) {
                    movement.velocity.x = -GameConfig.PLAYER_VELOCITY * 1f;
                    movement.velocity.y = GameConfig.PLAYER_VELOCITY * 0.5f;
                }


//        if(Math.abs(movement.velocity.x) < MIN_STOP_VELOCITY) movement.velocity.x = 0;
//        else movement.velocity.x += reduceVelocity(deltaTime, movement.velocity.x);
//
//        if(Math.abs(movement.velocity.y) < MIN_STOP_VELOCITY) movement.velocity.y = 0;
//        else movement.velocity.y += reduceVelocity(deltaTime, movement.velocity.y);
            }
        }

    }

    private float reduceVelocity(float deltaTime, float val){
        if(val > 0){
            return -(deltaTime * GameConfig.STOPPING_SPEED);
        }else if(val < 0){
            return deltaTime * GameConfig.STOPPING_SPEED;
        }
        return 0;
    }
}
