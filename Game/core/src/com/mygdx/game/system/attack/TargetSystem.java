package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.EndlessModeScreen;

import java.util.Random;

public class TargetSystem extends EntitySystem {

    private final EndlessModeScreen screen;

    private static final Logger log = new Logger(TargetSystem.class.getName(), Logger.DEBUG);

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            PositionOnGridComponent.class
    ).get();

    public TargetSystem(EndlessModeScreen screen) {
        this.screen = screen;
    }

    public int selectTargetX() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        Random random = new Random();

        for (Entity entity : player) {
            PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);
            int k = random.nextInt(3);
            if (!(position.xNumber + 1 - k < 0) && !(position.xNumber + 1 - k > screen.x - 1)) {
                log.debug("x = " + (position.xNumber + 1 - k));
                return position.xNumber + 1 - k;
            }
        }

        int pos = random.nextInt(screen.x);
        log.debug("x = " + pos);
        return pos;
    }

    public int selectTargetY() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        Random random = new Random();

        for (Entity entity : player) {
            PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);
            int k = random.nextInt(3);
            if (!(position.yNumber + 1 - k < 0) && !(position.yNumber + 1 - k > screen.y - 1)) {
                log.debug("y = " + (position.yNumber + 1 - k));
                return position.yNumber + 1 - k;
            }
        }

        int pos = random.nextInt(screen.y);
        log.debug("y = " + pos);
        return pos;
    }
}
