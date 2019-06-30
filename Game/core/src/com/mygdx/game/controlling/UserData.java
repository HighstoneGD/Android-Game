package com.mygdx.game.controlling;

import java.util.List;

public class UserData {

    public String name;
    public List<Long> highscores;

    public UserData(String name, List<Long> highscores) {
        this.name = name;
        this.highscores = highscores;
    }
}
