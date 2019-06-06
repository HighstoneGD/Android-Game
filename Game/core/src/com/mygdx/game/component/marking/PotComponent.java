package com.mygdx.game.component.marking;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.common.enums.PotType;

public class PotComponent implements Component {
    public PotType type;
    public float progress;
    public float aimX;
    public float aimY;
}
