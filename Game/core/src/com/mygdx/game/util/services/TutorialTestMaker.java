package com.mygdx.game.util.services;

public class TutorialTestMaker {

    public static final String FIRST = "Avoid pots. Shadows \n show, where pots will\n drop. \n" +
            "Simple pot deals \n 3 damage int the center \n and 1 damage on up, down \n left and right.";

    public static String getText(int lvl) {
        switch (lvl) {
            case 1: return FIRST;
        }

        return "";
    }
}
