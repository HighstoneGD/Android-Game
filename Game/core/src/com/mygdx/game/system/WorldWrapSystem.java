package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.BasicGameScreen;

public class WorldWrapSystem extends EntitySystem {

    private final ImmutableArray<Entity> players;
    private final Family PLAYER_POS_FAMILY = Family.all(
            PositionOnGridComponent.class
    ).get();
    private final BasicGameScreen screen;

    public WorldWrapSystem(BasicGameScreen screen, PooledEngine engine) {
        players = engine.getEntitiesFor(PLAYER_POS_FAMILY);
        this.screen = screen;
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : players) {
            PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);

            if (position.xNumber < 0) {
                position.xNumber = 0;
            }

            if (position.xNumber > screen.x - 1) {
                position.xNumber = screen.x - 1;
            }

            if (position.yNumber < 0) {
                position.yNumber = 0;
            }

            if (position.yNumber > screen.y - 1) {
                position.yNumber = screen.y - 1;
            }
        }
    }
}
