package com.mygdx.game.controlling;

import java.util.List;

public class UserData {

    public String name;
    public List<Integer> highscores;

    public UserData(String name, List<Integer> highscores) {
        this.name = name;
        this.highscores = highscores;
    }
}
