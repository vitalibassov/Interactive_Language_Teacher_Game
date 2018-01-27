package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vb.ilt.config.GameConfig;
import com.vb.ilt.entity.Direction;
import com.vb.ilt.entity.components.AnimationComponent;
import com.vb.ilt.entity.components.DirectionComponent;
import com.vb.ilt.entity.components.MovementComponent;
import com.vb.ilt.entity.components.PlayerComponent;
import com.vb.ilt.entity.components.SoundComponent;
import com.vb.ilt.entity.components.hud.ControlsComponent;
import com.vb.ilt.util.Mappers;

public class PlayerControlSystem extends EntitySystem {

    private final Viewport hudViewport;

    //Inappropriate behavior if the value is lower
    private static final float MIN_STOP_VELOCITY = 0.05f;

    public static final Family PLAYER = Family.all(
            PlayerComponent.class,
            MovementComponent.class,
            AnimationComponent.class,
            SoundComponent.class,
            DirectionComponent.class
    ).get();

    public static final Family CONTROLS = Family.all(
            ControlsComponent.class
    ).get();

    public PlayerControlSystem(Viewport hudViewport) {
        this.hudViewport = hudViewport;
    }

    @Override
    public void update(float deltaTime) {
        Entity player = getEngine().getEntitiesFor(PLAYER).first();
        Entity control = getEngine().getEntitiesFor(CONTROLS).first();

        MovementComponent movement = Mappers.MOVEMENT.get(player);
        AnimationComponent animation = Mappers.ANIMATION.get(player);
        ControlsComponent controlsComp = Mappers.CONTROLS.get(control);
        DirectionComponent direction = Mappers.DIRECTION.get(player);

        controlHandling(movement, animation, controlsComp, direction);
    }

    private void controlHandling(MovementComponent movement, AnimationComponent animation, ControlsComponent controlsComp, DirectionComponent direction) {
        movement.velocity.setZero();
        Vector2 worldTouch = getWorldTouch();
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || controlsComp.topRight.contains(worldTouch)) {
            applyDirection(movement.velocity, animation, direction, Direction.UP, 1, 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || controlsComp.bottomLeft.contains(worldTouch)) {
            applyDirection(movement.velocity, animation, direction, Direction.DOWN, -1, -1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controlsComp.bottomRight.contains(worldTouch)) {
            applyDirection(movement.velocity, animation, direction, Direction.RIGHT, 1, -1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || controlsComp.topLeft.contains(worldTouch)) {
            applyDirection(movement.velocity, animation, direction, Direction.LEFT, -1, 1);
        } else {
            applyDirection(movement.velocity, animation, direction, Direction.IDLE, 0, 0);
        }
    }

    private void applyDirection (Vector2 velocity, AnimationComponent animationComp, DirectionComponent directionComp, Direction direction, float x, float y){
        velocity.x = GameConfig.PLAYER_VELOCITY * 1f * x;
        velocity.y = GameConfig.PLAYER_VELOCITY * 0.5f * y;
        animationComp.setAnimationIndex(direction.getValue());
        directionComp.direction = direction;
    }

    private Vector2 getWorldTouch(){
        if (Gdx.input.isTouched()) {
            return hudViewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        }
        return new Vector2();
    }

    private float reduceVelocity(float deltaTime, float val) {
        if (val > 0) {
            return -(deltaTime * GameConfig.STOPPING_SPEED);
        } else if (val < 0) {
            return deltaTime * GameConfig.STOPPING_SPEED;
        }
        return 0;
    }
}
