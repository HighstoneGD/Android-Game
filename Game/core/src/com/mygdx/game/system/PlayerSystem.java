package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.PositionOnGridComponent;

public class PlayerSystem extends IteratingSystem {

    private static final Logger log = new Logger(PlayerSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            MovementStateComponent.class,
            PositionOnGridComponent.class
    ).get();

    public PlayerSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementStateComponent movementState = Mappers.MOVEMENT_STATE.get(entity);
        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(entity);

        if (!movementState.isMoving()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && positionOnGrid.number % 5 != 0) {
                moveLeft(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && positionOnGrid.number % 5 != 4) {
                moveRight(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && positionOnGrid.number > 4) {
                moveUp(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && positionOnGrid.number < 21) {
                moveDown(positionOnGrid);
            }
        }
    }

    private void moveLeft(PositionOnGridComponent position) {
        getEngine().getSystem(TimerSystem.class).startTimer();
        position.number--;
    }

    private void moveRight(PositionOnGridComponent position) {
        getEngine().getSystem(TimerSystem.class).startTimer();
        position.number++;
    }

    private void moveUp(PositionOnGridComponent position) {
        getEngine().getSystem(TimerSystem.class).startTimer();
        position.number -= 5;
    }

    private void moveDown(PositionOnGridComponent position) {
        getEngine().getSystem(TimerSystem.class).startTimer();
        position.number += 5;
    }
}
