package com.mygdx.game.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.marking.BackgroundComponent;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.marking.PlayerComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.TextureComponent;
import com.mygdx.game.system.debug.PositionsCalculationSystem;

import java.util.Random;

public class EntityFactory {

    private static final Logger log = new Logger(EntityFactory.class.getName(), Logger.DEBUG);
    private final PooledEngine engine;
    private final AssetManager assetManager;
    private final TextureAtlas gameplayBgAtlas;
    private final Random random;

    public EntityFactory(PooledEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
        random = new Random();
        gameplayBgAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_BG);
    }

    public void addCell(float x, float y, int xNumber, int yNumber, float height) {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = y;

        NumberComponent numberComponent = engine.createComponent(NumberComponent.class);
        numberComponent.xNumber = xNumber;
        numberComponent.yNumber = yNumber;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.bounds.set(x, y, height * 2, height);
        bounds.color = Color.GREEN;

        CellComponent cellComponent = engine.createComponent(CellComponent.class);

        AttackStateComponent attackState = engine.createComponent(AttackStateComponent.class);

        BonusComponent bonus = engine.createComponent(BonusComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.render = true;
        texture.region = gameplayBgAtlas.findRegion(RegionNames.CELL);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = height * 2f;
        dimension.height = height;

        BackgroundComponent backgroundComponent = engine.createComponent(BackgroundComponent.class);

        Entity entity = engine.createEntity();
        entity.add(backgroundComponent);
        entity.add(position);
        entity.add(bounds);
        entity.add(cellComponent);
        entity.add(attackState);
        entity.add(bonus);
        entity.add(numberComponent);
        entity.add(texture);
        entity.add(dimension);

        engine.addEntity(entity);
    }

    public void addPlayer() {
        PositionOnGridComponent position = engine.createComponent(PositionOnGridComponent.class);
        position.xNumber = engine.getSystem(PositionsCalculationSystem.class).positions.length / 2;
        position.yNumber = engine.getSystem(PositionsCalculationSystem.class).positions[0].length / 2;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.bounds.set(
                engine.getSystem(PositionsCalculationSystem.class).positions[position.xNumber][position.yNumber][0],
                engine.getSystem(PositionsCalculationSystem.class).positions[position.xNumber][position.yNumber][1],
                Constants.PLAYER_SIZE,
                Constants.PLAYER_SIZE
        );
        bounds.color = Color.CYAN;

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

    public void addBackground() {
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.render = true;
        texture.region = gameplayBgAtlas.findRegion(RegionNames.BACKGROUND);

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = Constants.WORLD_CENTER_X;
        position.y = Constants.WORLD_CENTER_Y;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = Constants.WORLD_WIDTH;
        dimension.height = Constants.WORLD_HEIGHT;

        BackgroundComponent backgroundComponent = engine.createComponent(BackgroundComponent.class);

        Entity entity = engine.createEntity();
        entity.add(texture);
        entity.add(position);
        entity.add(dimension);
        entity.add(backgroundComponent);

        engine.addEntity(entity);
    }
}
