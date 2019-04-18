package com.mygdx.game.system.moving;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.PositionOnGridComponent;

public class PlayerSystem extends IteratingSystem {

    private static final Logger log = new Logger(PlayerSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            MovementStateComponent.class,
            PositionOnGridComponent.class
    ).get();

    public PositionOnGridComponent positionOnGrid;

    public PlayerSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementStateComponent movementState = Mappers.MOVEMENT_STATE.get(entity);
        positionOnGrid = Mappers.POSITION_ON_GRID.get(entity);

        if (!movementState.isMoving() && Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                moveLeft(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                moveRight(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                moveUp(positionOnGrid);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                moveDown(positionOnGrid);
            }
        } else if (!movementState.isMoving() && Gdx.app.getType() == Application.ApplicationType.Android) {

        }
    }

    public void moveLeft(PositionOnGridComponent position) {
        TimerSystem timerSystem = new TimerSystem();
        getEngine().addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
        try {
            position.xNumber--;
            GameManager.INSTANCE.incrementLefts();
        } catch (Exception e) {}
    }

    public void moveRight(PositionOnGridComponent position) {
        TimerSystem timerSystem = new TimerSystem();
        getEngine().addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
        try {
            position.xNumber++;
            GameManager.INSTANCE.incrementRights();
        } catch (Exception e) {}
    }

    public void moveUp(PositionOnGridComponent position) {
        TimerSystem timerSystem = new TimerSystem();
        getEngine().addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
        try {
            position.yNumber--;
            GameManager.INSTANCE.incrementUps();
        } catch (Exception e) {}
    }

    public void moveDown(PositionOnGridComponent position) {
        TimerSystem timerSystem = new TimerSystem();
        getEngine().addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
        try {
            position.yNumber++;
            GameManager.INSTANCE.incrementDowns();
        } catch (Exception e) {}
    }
}
