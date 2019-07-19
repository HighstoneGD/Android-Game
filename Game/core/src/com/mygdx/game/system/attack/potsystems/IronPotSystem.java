package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.util.services.ObjectCreator;

public class IronPotSystem extends PotSystem {

    public IronPotSystem(int x, int y, BasicGameScreen screen) {
        super(screen, PotType.IRON, x, y);
    }

    @Override
    public void attack() {
        for (Entity cell : cells) {
            PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(cell);

            if (positionOnGrid.xNumber == x && positionOnGrid.yNumber == y) {
                ObjectCreator.createDamageObject(cell, GameData.IRON_CENTRAL_DAMAGE, GameData.POT_EXISTENCE_TIME);
            }

        }
        screen.potThrown();
        engine.removeSystem(this);
    }

}
