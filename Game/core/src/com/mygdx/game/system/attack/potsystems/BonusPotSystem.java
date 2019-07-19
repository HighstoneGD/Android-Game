package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.util.services.ObjectCreator;

public class BonusPotSystem extends PotSystem {

    private BonusType bonusType;

    public BonusPotSystem(int x, int y, BasicGameScreen screen, BonusType type) {
        super(screen, PotType.BONUS, x, y);
        this.bonusType = type;
    }

    @Override
    public void attack() {
        for (Entity cell : cells) {
            PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(cell);

            if (positionOnGrid.xNumber == x && positionOnGrid.yNumber == y) {
                ObjectCreator.createDamageObject(cell, GameData.BONUS_DAMAGE, GameData.POT_EXISTENCE_TIME);
                ObjectCreator.createBonusObject(cell, GameData.BONUS_EXISTENCE_TIME, bonusType);
                return;
            }
        }
        screen.potThrown();
        engine.removeSystem(this);
    }
}
