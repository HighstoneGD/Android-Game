package com.mygdx.game.component.marking;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.common.enums.Directions;

public class PlayerComponent implements Component {

    public boolean isAnimating;
    public Directions goesOnDirection;
    public float elapsedTime;
}
