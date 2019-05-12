package com.mygdx.game.system.render.animators;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Constants;
import com.mygdx.game.screen.BasicGameScreen;

public class AnimatorUtils {

    private static final Logger log = new Logger(AnimatorUtils.class.getName(), Logger.DEBUG);
    private static final float startX = 28f;
    private static final float startY = 88f;

    public static void createAnimator(BasicGameScreen screen, float cellX, float cellY, int yNumber) {
        SimpleAnimator animator = new SimpleAnimator(screen, cellX, cellY, yNumber);
        Thread thread = new Thread(animator);
        thread.start();
        log.debug("animator launched");
    }

    public static float calculateK(float cellX, float cellY) {
        float k = (startY - cellY) / (Math.abs(cellX - startX));

        // k = tg; startY - cellY = opposite kathetus; abs(cellX - startX) = contiguous kathetus
        return k;
    }

    public static float calculateB(float k) {
        float b = startY - startX * k;
        return b;
    }

    public static float getX(float duration, float elapsedTime, float cellX) {
        float percentageInFloat = elapsedTime / duration;
        float deltaX = cellX - startX;
        float x = startX + percentageInFloat * deltaX;
        return x;
    }

    public static float getY(float k, float b, float x) {
        return k * x + b;
    }

    public static float getDimensionCoefficient(int yNumber, int raws) {
        float dimCoef = (Constants.MAX_SIZE_COEF - 1f) / raws * yNumber + 1f;
        return dimCoef;
    }

    public static float getWidth(float coef, float duration, float elapsedTime) {
        float finalCoef = (coef - 1f) * elapsedTime / duration + 1f;
        return Constants.MIN_POT_WIDTH * finalCoef;
    }

    public static float getHeight(float coef, float duration, float elapsedTime) {
        float finalCoef = (coef - 1f) * elapsedTime / duration + 1f;
        return Constants.MIN_POT_HEIGHT * finalCoef;
    }

    private AnimatorUtils() {}
}
