package com.mygdx.game.controlling;

public class AvoidedPotsManager {

    private static int avoided;
    private static int avoidedRecord;

    public static void avoided() {
        avoided++;
    }

    public static void resetAvoided() {
        if (avoided > avoidedRecord) {
            avoidedRecord = avoided;
        }

        avoided = 0;
    }

    public static void reset() {
        avoided = 0;
        avoidedRecord = 0;
    }

}
