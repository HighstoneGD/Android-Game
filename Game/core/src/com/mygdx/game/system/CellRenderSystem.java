package com.mygdx.game.system;

import com.badlogic.ashley.core.EntitySystem;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.debug.GameConfig;

public class CellRenderSystem extends EntitySystem {

    private final EntityFactory factory;

    public CellRenderSystem(EntityFactory factory) {
        this.factory = factory;
    }

    public void spawnCells() {

        float xDifference = 0.8f;
        float yPosition = 4.75f;
        float heightDifference = 0f;

        for (int i = 0; i < 5; i++) {
            factory.addCell(3f, yPosition, GameConfig.CELL_HEIGHT + heightDifference);
            factory.addCell(3f + xDifference, yPosition, GameConfig.CELL_HEIGHT + heightDifference);
            factory.addCell(3f - xDifference, yPosition, GameConfig.CELL_HEIGHT + heightDifference);
            factory.addCell(3f + xDifference * 2f, yPosition, GameConfig.CELL_HEIGHT + heightDifference);
            factory.addCell(3f - xDifference * 2f, yPosition, GameConfig.CELL_HEIGHT + heightDifference);
            xDifference += 0.05f;
            yPosition -= 0.5f;
            heightDifference += 0.01f;
        }
    }
}
