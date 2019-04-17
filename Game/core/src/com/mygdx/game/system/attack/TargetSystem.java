package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.debug.GameConfig;
import com.mygdx.game.screen.game.EndlessModeScreen;

import java.util.Random;

public class TargetSystem extends EntitySystem {

    public TargetSystem(EndlessModeScreen screen) {
        random = new Random();
        this.screen = screen;
    }

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            PositionOnGridComponent.class
    ).get();
    private Random random;
    private EndlessModeScreen screen;
    private static final Logger log = new Logger(TargetSystem.class.getName(), Logger.DEBUG);

    public int selectTargetX() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        int x;
        int playerX = 1;

        int k = random.nextInt(GameConfig.CHANCE_FOR_RANDOM_TARGET);

        if (k == 0) {
            x = random.nextInt(screen.x);
        } else {

            for (Entity entity : player) {
                PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);
                playerX = position.xNumber;
            }

            if (GameManager.INSTANCE.leftOrRight() == 0) {
                x = playerX - 1;
            } else {
                x = playerX + 1;
            }

        }

        log.debug("x = " + x);
        return x;
    }

    public int selectTargetY() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        int y;
        int playerY = 1;

        int k = random.nextInt(GameConfig.CHANCE_FOR_RANDOM_TARGET);

        if (k == 0) {
            y = random.nextInt(screen.y);
        } else {

            for (Entity entity : player) {
                PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);
                playerY = position.yNumber;
            }

            if (GameManager.INSTANCE.upOrDown() == 0) {
                y = playerY - 1;
            } else {
                y = playerY + 1;
            }

        }

        log.debug("y = " + y);
        return y;
    }
}
