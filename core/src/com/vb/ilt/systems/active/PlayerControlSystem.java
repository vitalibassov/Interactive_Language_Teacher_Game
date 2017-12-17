package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.vb.ilt.components.MovementComponent;
import com.vb.ilt.components.PlayerComponent;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.util.Mappers;

public class PlayerControlSystem extends IteratingSystem{

    //Inappropriate behavior if the value is lower
    private static final float MIN_STOP_VELOCITY = 0.05f;

    public static final Family FAMILY = Family.all(
            PlayerComponent.class,
            MovementComponent.class
    ).get();

    public PlayerControlSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = Mappers.MOVEMENT.get(entity);



        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            movement.velocity.y = GameConfig.PLAYER_VELOCITY;
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            movement.velocity.y = -GameConfig.PLAYER_VELOCITY;
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            movement.velocity.x = GameConfig.PLAYER_VELOCITY * 1f;
           // movement.velocity.y = -GameConfig.PLAYER_VELOCITY * 0.54f;
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            movement.velocity.x = -GameConfig.PLAYER_VELOCITY;
        }

        if(Math.abs(movement.velocity.x) < MIN_STOP_VELOCITY) movement.velocity.x = 0;
        else movement.velocity.x += reduceVelocity(deltaTime, movement.velocity.x);

        if(Math.abs(movement.velocity.y) < MIN_STOP_VELOCITY) movement.velocity.y = 0;
        else movement.velocity.y += reduceVelocity(deltaTime, movement.velocity.y);
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
