package com.mygdx.game.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.Directions;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.marking.PlayerComponent;

public class PlayerRenderSystem extends IteratingSystem {

    private static final Family PLAYER = Family.all(
            PlayerComponent.class
    ).get();
    private SpriteBatch batch;
    private Viewport viewport;

    private Texture playerStatic;
    private Animation<TextureRegion> left;
    private Animation<TextureRegion> right;
    private Animation<TextureRegion> vertical;

    private static final Logger log = new Logger(PlayerRenderSystem.class.getName(), Logger.DEBUG);

    public PlayerRenderSystem(SpriteBatch batch, Viewport viewport, AssetManager assetManager) {
        super(PLAYER);
        this.batch = batch;
        this.viewport = viewport;

        playerStatic = assetManager.get(AssetDescriptors.PLAYER_STATIC);
        left = new Animation<TextureRegion>(Constants.PLAYER_FRAME_TIME, assetManager.get(AssetDescriptors.PLAYER_LEFT_JUMP).getRegions());
        right = new Animation<TextureRegion>(Constants.PLAYER_FRAME_TIME, assetManager.get(AssetDescriptors.PLAYER_RIGHT_JUMP).getRegions());
        vertical = new Animation<TextureRegion>(Constants.PLAYER_FRAME_TIME, assetManager.get(AssetDescriptors.PLAYER_VERTICAL_JUMP).getRegions());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = Mappers.PLAYER.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        if (!playerComponent.isAnimating) {
            drawStatic(position, dimension);
        } else if (playerComponent.isAnimating) {

            playerComponent.elapsedTime += deltaTime;

            if (playerComponent.goesOnDirection == Directions.LEFT) {
                draw(playerComponent, position, dimension, left);
            } else if (playerComponent.goesOnDirection == Directions.RIGHT) {
                draw(playerComponent, position, dimension, right);
            } else if (playerComponent.goesOnDirection == Directions.UP || playerComponent.goesOnDirection == Directions.DOWN) {
                draw(playerComponent, position, dimension, vertical);
            }

        }
    }

    private void drawStatic(PositionComponent position, DimensionComponent dimension) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(
                playerStatic,
                position.x - dimension.width / 2f, position.y - dimension.height / 2f,
                dimension.width, dimension.height
        );

        batch.end();
    }

    private void draw(PlayerComponent player, PositionComponent position, DimensionComponent dimension, Animation<TextureRegion> animation) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(
                animation.getKeyFrame(player.elapsedTime, false),
                position.x - dimension.width / 2f, position.y - dimension.height / 2f,
                dimension.width, dimension.height
        );

        batch.end();

        if (animation.isAnimationFinished(player.elapsedTime)) {
            player.isAnimating = false;
        }
    }
}
