package com.mygdx.game.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.CellComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionComponent;

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

    }
}
