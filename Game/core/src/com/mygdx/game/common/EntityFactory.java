package com.mygdx.game.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.AnimationComponent;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.PotComponent;
import com.mygdx.game.component.TextureComponent;
import com.mygdx.game.component.marking.BackgroundComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.component.marking.PlayerComponent;
import com.mygdx.game.component.marking.SmashComponent;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.debug.PositionsCalculationSystem;

import java.util.Random;

public class EntityFactory {

    private static final Logger log = new Logger(EntityFactory.class.getName(), Logger.DEBUG);
    private final PooledEngine engine;
    private final AssetManager assetManager;
    private final BasicGameScreen screen;
    private final TextureAtlas gameplayBgAtlas;
    private final Random random;

    public EntityFactory(BasicGameScreen screen) {
        this.screen = screen;
        this.engine = screen.getEngine();
        this.assetManager = screen.getAssetManager();
        gameplayBgAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_BG);
        random = new Random();
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
        cellComponent.hasPlayer = false;

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

    public void addSmash(PotType type, float x, float y) {
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        switch (type) {
            case SIMPLE: {
                animationComponent.animation = new Animation<TextureRegion>(Constants.FRAME_TIME,
                        assetManager.get(AssetDescriptors.SIMPLE_SMASH).getRegions());
                animationComponent.elapsedTime = 0;
            }
        }

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = y;

        SmashComponent smashComponent = engine.createComponent(SmashComponent.class);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        switch (type) {
            case SIMPLE: {
                dimension.width = Constants.SIMPLE_SMASH_WIDTH;
                dimension.height = Constants.SIMPLE_SMASH_HEIGHT;
            }
        }

        Entity entity = engine.createEntity();
        entity.add(animationComponent);
        entity.add(position);
        entity.add(smashComponent);
        entity.add(dimension);

        engine.addEntity(entity);
    }

    public void addPot(PotType type, float x, int xNumber, int yNumber) {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = Constants.WORLD_HEIGHT + 10f;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        float coef = (float) Math.pow(Constants.POT_SIZE_COEFFICIENT, screen.y - yNumber - 1);

        PositionOnGridComponent positionOnGrid = engine.createComponent(PositionOnGridComponent.class);
        positionOnGrid.xNumber = xNumber;
        positionOnGrid.yNumber = yNumber;

        PotComponent potComponent = engine.createComponent(PotComponent.class);
        potComponent.type = type;

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.elapsedTime = random.nextFloat();

        if (type == PotType.SIMPLE) {
            animationComponent.animation = new Animation<TextureRegion>(Constants.FRAME_TIME,
                        assetManager.get(AssetDescriptors.SIMPLE_TEXTURE).getRegions());
            dimension.width = Constants.SIMPLE_POT_WIDTH * coef;
            dimension.height = Constants.SIMPLE_POT_HEIGHT * coef;
        } else if (type == PotType.IRON) {
            animationComponent.animation = new Animation<TextureRegion>(Constants.FRAME_TIME,
                        assetManager.get(AssetDescriptors.IRON_TEXTURE).getRegions());
            dimension.width = Constants.IRON_POT_WIDTH * coef;
            dimension.height = Constants.IRON_POT_HEIGHT * coef;
        }

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(positionOnGrid);
        entity.add(potComponent);
        entity.add(animationComponent);

        engine.addEntity(entity);
    }
}
