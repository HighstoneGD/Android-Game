package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.mygdx.game.common.EntityFactory;

public class CellsSpawnSystem extends EntitySystem {

    private final EntityFactory factory;

    public CellsSpawnSystem(EntityFactory factory) {
        this.factory = factory;
    }

    public void spawnCells(int x, int y) {

    }
}
