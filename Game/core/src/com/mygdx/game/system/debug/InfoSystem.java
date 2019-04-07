package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionOnGridComponent;

public class InfoSystem extends EntitySystem {

    private static final Logger log = new Logger(InfoSystem.class.getName(), Logger.DEBUG);

    public InfoSystem() {}

    private final Family PLAYER_FAMILY = Family.all(
            BoundsComponent.class,
            PlayerComponent.class
    ).get();

    private final Family PLAYER_POS_FAMILY = Family.all(
            PositionOnGridComponent.class
    ).get();

    @Override
    public void update(float deltaTime) {
        for (Entity entity : getEngine().getEntitiesFor(PLAYER_FAMILY)) {
            BoundsComponent bc = Mappers.BOUNDS.get(entity);
            log.debug("player`s bounds position = " + bc.bounds.x + " " + bc.bounds.y);
        }

        for (Entity entity : getEngine().getEntitiesFor(PLAYER_POS_FAMILY)) {
            PositionOnGridComponent pos = Mappers.POSITION_ON_GRID.get(entity);
            log.debug("player`s position = " + pos.xNumber + " " + pos.yNumber);
        }
    }
}
