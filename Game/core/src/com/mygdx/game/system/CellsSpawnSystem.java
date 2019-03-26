package com.mygdx.game.system;

import com.badlogic.ashley.core.EntitySystem;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.debug.CellsPositions;
import com.mygdx.game.debug.GameConfig;

public class CellsSpawnSystem extends EntitySystem {

    private final EntityFactory factory;

    public CellsSpawnSystem(EntityFactory factory) {
        this.factory = factory;
    }

    public void spawnCells() {
        for (int i = 0; i < 25; i++) {
            factory.addCell(CellsPositions.getX(i), CellsPositions.getY(i), i, GameConfig.CELL_HEIGHT);
        }
    }
}
