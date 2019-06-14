package com.mygdx.game.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.component.AnimationComponent;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.OrderComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.ShadowComponent;
import com.mygdx.game.component.SpeedComponent;
import com.mygdx.game.component.marking.GranComponent;
import com.mygdx.game.component.marking.PotComponent;
import com.mygdx.game.component.TextureComponent;
import com.mygdx.game.component.marking.BackgroundComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.component.marking.PlayerComponent;
import com.mygdx.game.component.marking.SmashComponent;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.system.attack.potsystems.PotSystem;
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

        PositionOnGridComponent positionOnGrid = engine.createComponent(PositionOnGridComponent.class);
        positionOnGrid.xNumber = xNumber;
        positionOnGrid.yNumber = yNumber;

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
        entity.add(cellComponent);
        entity.add(attackState);
        entity.add(bonus);
        entity.add(positionOnGrid);
        entity.add(texture);
        entity.add(dimension);

        engine.addEntity(entity);
    }

    public void addPlayer() {
        PositionOnGridComponent positionOnGrid = engine.createComponent(PositionOnGridComponent.class);
        positionOnGrid.xNumber = engine.getSystem(PositionsCalculationSystem.class).positions.length / 2;
        positionOnGrid.yNumber = engine.getSystem(PositionsCalculationSystem.class).positions[0].length / 2;

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = 0;
        position.y = 0;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameData.PLAYER_SIZE;
        dimension.height = GameData.PLAYER_SIZE;

        MovementStateComponent movementState = engine.createComponent(MovementStateComponent.class);
        movementState.setMoving(false);

        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        player.isAnimating = false;

        SpeedComponent speed = engine.createComponent(SpeedComponent.class);
        speed.speedX = 0;
        speed.speedY = 0;

        Entity entity = engine.createEntity();
        entity.add(positionOnGrid);
        entity.add(position);
        entity.add(dimension);
        entity.add(player);
        entity.add(movementState);
        entity.add(speed);

        engine.addEntity(entity);
    }

    public void addBackground() {
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.render = true;
        texture.region = gameplayBgAtlas.findRegion(RegionNames.BACKGROUND);

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameData.WORLD_CENTER_X;
        position.y = GameData.WORLD_CENTER_Y;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameData.WORLD_WIDTH;
        dimension.height = GameData.WORLD_HEIGHT;

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
        animationComponent.elapsedTime = 0;

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = y;

        SmashComponent smashComponent = engine.createComponent(SmashComponent.class);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);

        if (type == PotType.SIMPLE) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                    assetManager.get(AssetDescriptors.SIMPLE_SMASH).getRegions());
            dimension.width = GameData.SIMPLE_SMASH_WIDTH;
            dimension.height = GameData.SIMPLE_SMASH_HEIGHT;
        } else if (type == PotType.IRON) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                    assetManager.get(AssetDescriptors.IRON_SMASH).getRegions());
            dimension.width = GameData.IRON_SMASH_WIDTH;
            dimension.height = GameData.IRON_SMASH_HEIGHT;
        } else if (type == PotType.LARGE) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                    assetManager.get(AssetDescriptors.LARGE_SMASH).getRegions());
            dimension.width = GameData.LARGE_SMASH_SIZE;
            dimension.height = GameData.LARGE_SMASH_SIZE;
        } else if (type == PotType.BONUS) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                    assetManager.get(AssetDescriptors.BONUS_SMASH).getRegions());
            dimension.width = GameData.BONUS_SMASH_SIZE;
            dimension.height = GameData.BONUS_SMASH_SIZE;
        } else if (type == PotType.EXPLOSIVE) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                    assetManager.get(AssetDescriptors.EXPLOSIVE_SMASH).getRegions());
            dimension.width = GameData.EXPLOSIVE_SMASH_SIZE;
            dimension.height = GameData.EXPLOSIVE_SMASH_SIZE;
        }

        OrderComponent order = engine.createComponent(OrderComponent.class);
        order.beforePlayer = false;

        Entity entity = engine.createEntity();
        entity.add(animationComponent);
        entity.add(position);
        entity.add(smashComponent);
        entity.add(dimension);
        entity.add(order);

        engine.addEntity(entity);
    }

    public void addPot(PotType type, float x, float y, int xNumber, int yNumber, PotSystem system) {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = GameData.WORLD_HEIGHT + 10f;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        float coef = (float) Math.pow(GameData.POT_SIZE_COEFFICIENT, screen.y - yNumber - 1);

        PositionOnGridComponent positionOnGrid = engine.createComponent(PositionOnGridComponent.class);
        positionOnGrid.xNumber = xNumber;
        positionOnGrid.yNumber = yNumber;

        PotComponent potComponent = engine.createComponent(PotComponent.class);
        potComponent.type = type;
        potComponent.progress = 0;
        potComponent.aimX = x;
        potComponent.aimY = y;
        potComponent.system = system;

        SpeedComponent speed = engine.createComponent(SpeedComponent.class);
        float distance = GameData.WORLD_HEIGHT + 10f - y;
        speed.speedY = distance / GameData.POT_FLIGHT_TIME;

        if (type == PotType.IRON) {
            speed.speedY *= 3;
        }

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.elapsedTime = random.nextFloat();

        if (type == PotType.SIMPLE) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                        assetManager.get(AssetDescriptors.SIMPLE_TEXTURE).getRegions());
            dimension.width = GameData.SIMPLE_POT_WIDTH * coef;
            dimension.height = GameData.SIMPLE_POT_HEIGHT * coef;
        } else if (type == PotType.IRON) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                        assetManager.get(AssetDescriptors.IRON_TEXTURE).getRegions());
            dimension.width = GameData.IRON_POT_WIDTH * coef;
            dimension.height = GameData.IRON_POT_HEIGHT * coef;
        } else if (type == PotType.LARGE) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                    assetManager.get(AssetDescriptors.LARGE_TEXTURE).getRegions());
            dimension.width = GameData.LARGE_POT_SIZE * coef;
            dimension.height = GameData.LARGE_POT_SIZE * coef;
        } else if (type == PotType.BONUS) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                    assetManager.get(AssetDescriptors.BONUS_TEXTURE).getRegions());
            dimension.width = GameData.BONUS_POT_SIZE * coef;
            dimension.height = GameData.BONUS_POT_SIZE * coef;
        } else if (type == PotType.EXPLOSIVE) {
            animationComponent.animation = new Animation<TextureRegion>(GameData.FRAME_TIME,
                    assetManager.get(AssetDescriptors.EXPLOSIVE_TEXTURE).getRegions());
            dimension.width = GameData.EXPLOSIVE_POT_SIZE * coef;
            dimension.height = GameData.EXPLOSIVE_POT_SIZE * coef;
        }

        ShadowComponent shadow = engine.createComponent(ShadowComponent.class);
        shadow.shadowHeight = 0;

        OrderComponent order = engine.createComponent(OrderComponent.class);
        order.beforePlayer = false;

        Entity entity = engine.createEntity();
        entity.add(speed);
        entity.add(position);
        entity.add(dimension);
        entity.add(positionOnGrid);
        entity.add(potComponent);
        entity.add(animationComponent);
        entity.add(shadow);
        entity.add(order);

        engine.addEntity(entity);
    }

    public void addGran() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameData.GRAN_X;
        position.y = GameData.GRAN_Y;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameData.GRAN_SIZE;
        dimension.height = GameData.GRAN_SIZE;

        GranComponent gran = engine.createComponent(GranComponent.class);
        gran.isAnimating = false;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(gran);

        engine.addEntity(entity);
    }
}
