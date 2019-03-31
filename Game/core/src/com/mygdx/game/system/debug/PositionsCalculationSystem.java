package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.mygdx.game.debug.Positions;

public class PositionsCalculationSystem extends EntitySystem {

    public PositionsCalculationSystem() {}

    public float[][][] calculatePositions(int x, int y) {
        float[][][] positions = new float[x][y][2];

        float[] rows = new float[y + 1];
        rows[rows.length - 1] = Positions.GRID_BOTTOM;
        rows[0] = Positions.GRID_TOP;

        float startDistance = startDistance(y);

        for (int i = 1; i < rows.length - 1; i++) {
            rows[i] = Positions.GRID_TOP - startDistance * (float)Math.pow(1.15f, i - 1);
        }

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                positions[i][j][1] = (rows[j + 1] - rows[j]) / 2f;
            }
        }

        return positions;
    }

    private float startDistance(int y) {
        return (float)(4.2f / (Math.pow(1.15f, y) - 1));
    }
}
