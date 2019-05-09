package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.BasicGameScreen;

import java.util.Random;

public class TargetSystem extends EntitySystem {

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            PositionOnGridComponent.class
    ).get();
    private Random random;
    private BasicGameScreen screen;

    public TargetSystem(BasicGameScreen screen) {
        random = new Random();
        this.screen = screen;
    }

    public int selectTargetX() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        int x;
        int playerX = 1;

        int k = random.nextInt(Constants.CHANCE_FOR_RANDOM_TARGET);

        if (k == 0) {
            x = random.nextInt(screen.x);
        } else {

            for (Entity entity : player) {
                PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);
                playerX = position.xNumber;
            }

            if (playerX == 0) {
                x = random.nextInt(2);
            } else if (playerX == screen.x - 1) {
                x = screen.x - 1 - random.nextInt(2);
            } else {
                x = playerX - 1 + random.nextInt(3);
            }

        }

        return x;
    }

    public int selectTargetY() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        int y;
        int playerY = 1;

        int k = random.nextInt(Constants.CHANCE_FOR_RANDOM_TARGET);

        if (k == 0) {
            y = random.nextInt(screen.y);
        } else {

            for (Entity entity : player) {
                PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);
                playerY = position.yNumber;
            }

            if (playerY == 0) {
                y = random.nextInt(2);
            } else if (playerY == screen.y - 1) {
                y = screen.y - 1 - random.nextInt(2);
            } else {
                y = playerY - 1 + random.nextInt(3);
            }

        }

        return y;
    }
}
