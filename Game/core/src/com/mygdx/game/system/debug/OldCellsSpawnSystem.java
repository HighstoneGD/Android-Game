package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.debug.OldCellsPositions;
import com.mygdx.game.debug.GameConfig;

@Deprecated
public class OldCellsSpawnSystem extends EntitySystem {

    private final EntityFactory factory;

    public OldCellsSpawnSystem(EntityFactory factory) {
        this.factory = factory;
    }

    public void spawnCells() {
        for (int i = 0; i < 25; i++) {
            factory.addCell(OldCellsPositions.getX(i), OldCellsPositions.getY(i), i, GameConfig.CELL_HEIGHT);
        }
    }
}
