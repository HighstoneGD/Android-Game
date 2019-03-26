package com.mygdx.game.component;

import com.badlogic.ashley.core.Component;

public class MovementStateComponent implements Component {

    private boolean isMoving;

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}
