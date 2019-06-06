package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.marking.PlayerComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.BasicGameScreen;

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
        int playerX;

        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(player.first());
        playerX = positionOnGrid.xNumber;

        return selectCoordinate(screen.x, playerX);
    }

    public int selectTargetY() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        int playerY;

        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(player.first());
        playerY = positionOnGrid.yNumber;

        return selectCoordinate(screen.y, playerY);
    }

    private int selectCoordinate(int limit, int playerPosition) {
        int coordinate;
        int k = random.nextInt(Constants.CHANCE_FOR_RANDOM_TARGET);

        if (k == 0) {
            coordinate = random.nextInt(limit);
        } else {

            if (playerPosition == 0) {
                coordinate = random.nextInt(2);
            } else if (playerPosition == limit - 1) {
                coordinate = limit - 1 - random.nextInt(2);
            } else {
                coordinate = playerPosition - 1 + random.nextInt(3);
            }

        }

        return coordinate;
    }
}
