package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.util.services.NumberConverter;
import com.mygdx.game.util.services.ObjectCreator;

public class ExplosivePotSystem extends PotSystem {

    private boolean left;
    private boolean up;

    public ExplosivePotSystem(int x, int y, BasicGameScreen screen) {
        super(screen, PotType.EXPLOSIVE, x, y);
        left = true;
        up = true;

        if (x == 0) {
            left = false;
        }

        if (y == 0) {
            up = false;
        }
    }

    @Override
    protected void initCellCoordinates() {
        cellX = engine.getSystem(NumberConverter.class).getCoordinates(x, y, left, up).x;
        cellY = engine.getSystem(NumberConverter.class).getCoordinates(x, y, left, up).y;
    }

    @Override
    public void attack() {
        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(cell);

            if (positionOnGrid.xNumber == x && positionOnGrid.yNumber == y) {
                ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
            } else if (positionOnGrid.xNumber == x) {

                if (up && positionOnGrid.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
                } else if (!up && positionOnGrid.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
                }

            } else if (left && positionOnGrid.xNumber == x - 1) {

                if (positionOnGrid.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
                } else if (up && positionOnGrid.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
                } else if (!up && positionOnGrid.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
                }

            } else if (!left && positionOnGrid.xNumber == x + 1) {

                if (positionOnGrid.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
                } else if (up && positionOnGrid.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
                } else if (!up && positionOnGrid.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, GameData.EXPLOSIVE_CENTRAL_DAMAGE, potExistenceTime);
                }

            }

        }
        screen.potThrown();
        engine.removeSystem(this);
    }

    @Override
    protected void initPotExistenceTime() {
        super.initPotExistenceTime();
        potExistenceTime *= 3;
    }
}
