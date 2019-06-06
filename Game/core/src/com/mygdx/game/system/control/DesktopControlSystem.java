package com.mygdx.game.system.control;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.Directions;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.marking.PlayerComponent;

public class DesktopControlSystem extends IteratingSystem {

    private static final Family PLAYER = Family.all(
            MovementStateComponent.class,
            PositionOnGridComponent.class,
            PlayerComponent.class
    ).get();

    public DesktopControlSystem() {
        super(PLAYER);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementStateComponent movementState = Mappers.MOVEMENT_STATE.get(entity);

        if (!movementState.isMoving()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                getEngine().getSystem(PlayerMovementSystem.class).movePlayer(Directions.LEFT);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                getEngine().getSystem(PlayerMovementSystem.class).movePlayer(Directions.RIGHT);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                getEngine().getSystem(PlayerMovementSystem.class).movePlayer(Directions.UP);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                getEngine().getSystem(PlayerMovementSystem.class).movePlayer(Directions.DOWN);
            }
        }
    }


}
