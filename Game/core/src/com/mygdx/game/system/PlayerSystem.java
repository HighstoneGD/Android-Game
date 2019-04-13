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
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                moveLeft(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                moveRight(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                moveUp(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                moveDown(positionOnGrid);
            }
        }
    }

    private void moveLeft(PositionOnGridComponent position) {
        TimerSystem timerSystem = new TimerSystem();
        getEngine().addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
        try {
            position.xNumber--;
        } catch (Exception e) {}
    }

    private void moveRight(PositionOnGridComponent position) {
        TimerSystem timerSystem = new TimerSystem();
        getEngine().addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
        try {
            position.xNumber++;
        } catch (Exception e) {}
    }

    private void moveUp(PositionOnGridComponent position) {
        TimerSystem timerSystem = new TimerSystem();
        getEngine().addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
        try {
            position.yNumber--;
        } catch (Exception e) {}
    }

    private void moveDown(PositionOnGridComponent position) {
        TimerSystem timerSystem = new TimerSystem();
        getEngine().addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
        try {
            position.yNumber++;
        } catch (Exception e) {}
    }
}
