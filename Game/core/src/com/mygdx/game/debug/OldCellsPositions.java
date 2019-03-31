package com.mygdx.game.debug;

@Deprecated
public class OldCellsPositions {

    private static final float[] Y = {
            51.8f,
            47.1f,
            41.9f,
            36.1f,
            29.64f
    };

    private static final float[] X = {
            16.1f, 23.05f, 30f, 36.95f, 43.9f,
            14.8f, 22.4f, 30f, 37.6f, 45.2f,
            13.45f, 21.725f, 30f, 38.275f, 46.55f,
            11.9f, 21f, 30f, 39f, 48.1f,
            10.17f, 20.1f, 30f, 39.9f, 49.83f
    };

    public static float getX(int number) {
        return X[number];
    }

    public static float getY(int number) {
        return Y[number / 5];
    }

    private OldCellsPositions() {
    }
}
