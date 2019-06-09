package com.mygdx.game.system.control;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.Directions;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.SpeedComponent;
import com.mygdx.game.component.marking.PlayerComponent;
import com.mygdx.game.util.logic.NumberConverter;
import com.mygdx.game.util.logic.SystemCreator;

public class PlayerMovementSystem extends EntitySystem {

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            SpeedComponent.class
    ).get();
    private PooledEngine engine;
    private ImmutableArray<Entity> player;

    private static final Logger log = new Logger(PlayerMovementSystem.class.getName(), Logger.DEBUG);

    private PositionComponent position;
    private PositionOnGridComponent positionOnGrid;
    private MovementStateComponent movementState;
    private SpeedComponent speed;
    private PlayerComponent playerComponent;

    public PlayerMovementSystem(PooledEngine engine) {
        this.engine = engine;
        player = engine.getEntitiesFor(PLAYER);
    }

    @Override
    public void update(float deltaTime) {
        if (position == null) {
            position = Mappers.POSITION.get(player.first());
            positionOnGrid = Mappers.POSITION_ON_GRID.get(player.first());
            movementState = Mappers.MOVEMENT_STATE.get(player.first());
            speed = Mappers.SPEED.get(player.first());
            playerComponent = Mappers.PLAYER.get(player.first());
        }

        if (!movementState.isMoving()) {

            position.x = getCellX(0, 0);
            position.y = getCellY(0, 0);

        } else if (movementState.isMoving()) {

            position.x += speed.speedX * deltaTime;
            position.y += speed.speedY * deltaTime;

        }
    }

    public void movePlayer(Directions direction) {
        playerComponent.isAnimating = true;
        playerComponent.elapsedTime = 0;

        if (direction == Directions.UP) {
            moveUp();
        } else if (direction == Directions.DOWN) {
            moveDown();
        } else if (direction == Directions.RIGHT) {
            moveRight();
        } else if (direction == Directions.LEFT) {
            moveLeft();
        }
    }

    public Directions getDirection() {
        return playerComponent.goesOnDirection;
    }

    private void moveLeft() {
        float cellX = getCellX(-1, 0);
        float cellY = getCellY(-1, 0);

        if (cellX != 0) {
            calculateSpeed(cellX, cellY);
            playerComponent.goesOnDirection = Directions.LEFT;

            SystemCreator.createTimer(engine);
            try {
                positionOnGrid.xNumber--;
            } catch (Exception e) {
            }
        }
    }

    private void moveRight() {
        float cellX = getCellX(1, 0);
        float cellY = getCellY(1, 0);

        if (cellX != 0) {
            calculateSpeed(cellX, cellY);
            playerComponent.goesOnDirection = Directions.RIGHT;

            SystemCreator.createTimer(engine);
            try {
                positionOnGrid.xNumber++;
            } catch (Exception e) {
            }
        }
    }

    private void moveUp() {
        float cellX = getCellX(0, -1);
        float cellY = getCellY(0, -1);

        if (cellY != 0) {
            calculateSpeed(cellX, cellY);
            playerComponent.goesOnDirection = Directions.UP;

            SystemCreator.createTimer(engine);
            try {
                positionOnGrid.yNumber--;
            } catch (Exception e) {
            }
        }
    }

    private void moveDown() {
        float cellX = getCellX(0, 1);
        float cellY = getCellY(0, 1);

        if (cellY != 0) {
            calculateSpeed(cellX, cellY);
            playerComponent.goesOnDirection = Directions.DOWN;

            SystemCreator.createTimer(engine);
            try {
                positionOnGrid.yNumber++;
            } catch (Exception e) {
            }
        }
    }

    private void calculateSpeed(float cellX, float cellY) {
        speed.speedX = calculateDistanceX(cellX) / (Constants.PLAYER_JUMP_TIME / 1000f);
        speed.speedY = calculateDistanceY(cellY) / (Constants.PLAYER_JUMP_TIME / 1000f);
    }

    private float calculateDistanceX(float cellX) {
        return cellX - position.x;
    }

    private float calculateDistanceY(float cellY) {
        return cellY - position.y;
    }

    private float getCellX(int xDeviation, int yDeviation) {
        return engine.getSystem(NumberConverter.class).getCoordinates(positionOnGrid.xNumber + xDeviation, positionOnGrid.yNumber + yDeviation).x;
    }

    private float getCellY(int xDeviation, int yDeviation) {
        return engine.getSystem(NumberConverter.class).getCoordinates(positionOnGrid.xNumber + xDeviation, positionOnGrid.yNumber + yDeviation).y;
    }
}
