package com.mygdx.game.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.CellComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.debug.CellsPositions;
import com.mygdx.game.debug.GameConfig;

public class EntityFactory {

    private final PooledEngine engine;
    private final AssetManager assetManager;

    public EntityFactory(PooledEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
    }

    public void addCell(float x, float y, int number, float height) {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = y;

        NumberComponent numberComponent = engine.createComponent(NumberComponent.class);
        numberComponent.number = number;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.bounds.set(x, y, height * 2, height);

        CellComponent cellComponent = engine.createComponent(CellComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(bounds);
        entity.add(cellComponent);

        engine.addEntity(entity);
    }

    public void addPlayer() {
        int positionOnGrid = GameConfig.PLAYER_START_POSITION;

        PositionOnGridComponent position = engine.createComponent(PositionOnGridComponent.class);
        position.number = positionOnGrid;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.bounds.set(
                CellsPositions.getX(position.number),
                CellsPositions.getY(position.number),
                GameConfig.PLAYER_SIZE,
                GameConfig.PLAYER_SIZE
        );

        MovementStateComponent movementState = engine.createComponent(MovementStateComponent.class);
        movementState.setMoving(false);

        PlayerComponent player = engine.createComponent(PlayerComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(bounds);
        entity.add(player);
        entity.add(movementState);

        engine.addEntity(entity);
    }
}
