package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.util.logic.NumberConverter;
import com.mygdx.game.util.logic.ObjectCreator;
import com.mygdx.game.util.objects.Bonus;

public class BonusPotSystem extends PotSystem {

    private BonusType bonusType;

    public BonusPotSystem(int x, int y, BasicGameScreen screen, BonusType type) {
        super(screen, PotType.BONUS, x, y);
        this.bonusType = type;
    }

    @Override
    protected void attack() {
        for (Entity cell : cells) {
            PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(cell);

            if (positionOnGrid.xNumber == x && positionOnGrid.yNumber == y) {
                ObjectCreator.createDamageObject(cell, GameData.BONUS_DAMAGE, GameData.POT_EXISTENCE_TIME);
                ObjectCreator.createBonusObject(cell, GameData.BONUS_EXISTENCE_TIME, bonusType);
                return;
            }
        }
    }
}
