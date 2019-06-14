package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.util.logic.NumberConverter;
import com.mygdx.game.util.logic.ObjectCreator;

public class LargePotSystem extends PotSystem {

    public LargePotSystem(int x, int y, BasicGameScreen screen) {
        super(screen, PotType.LARGE, x, y);
    }

    @Override
    public void attack() {
        for (Entity cell : cells) {
            PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(cell);

            if (positionOnGrid.xNumber == x) {

                if (positionOnGrid.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, GameData.LARGE_CENTRAL_DAMAGE, GameData.POT_EXISTENCE_TIME);
                } else if (positionOnGrid.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, GameData.SHARD_DAMAGE, GameData.POT_EXISTENCE_TIME);
                } else if (positionOnGrid.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, GameData.SHARD_DAMAGE, GameData.POT_EXISTENCE_TIME);
                }

            }

            if (positionOnGrid.xNumber == x - 1) {

                if (positionOnGrid.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, GameData.SHARD_DAMAGE, GameData.POT_EXISTENCE_TIME);
                } else if (positionOnGrid.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, GameData.SHARD_DAMAGE, GameData.POT_EXISTENCE_TIME);
                } else if (positionOnGrid.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, GameData.SHARD_DAMAGE, GameData.POT_EXISTENCE_TIME);
                }

            }

            if (positionOnGrid.xNumber == x + 1) {

                if (positionOnGrid.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, GameData.SHARD_DAMAGE, GameData.POT_EXISTENCE_TIME);
                } else if (positionOnGrid.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, GameData.SHARD_DAMAGE, GameData.POT_EXISTENCE_TIME);
                } else if (positionOnGrid.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, GameData.SHARD_DAMAGE, GameData.POT_EXISTENCE_TIME);
                }

            }

        }
        screen.potThrown();
        engine.removeSystem(this);
    }
}
