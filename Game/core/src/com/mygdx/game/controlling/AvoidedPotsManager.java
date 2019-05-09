package com.mygdx.game.controlling;

public class AvoidedPotsManager {

    private static int avoided = 0;
    private static int avoidedRecord = 0;

    public void avoided() {
        avoided++;
    }

    public void resetAvoided() {
        if (avoided > avoidedRecord) {
            avoidedRecord = avoided;
        }

        avoided = 0;
    }

}
