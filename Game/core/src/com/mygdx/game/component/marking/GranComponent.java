package com.mygdx.game.component.marking;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.common.enums.PotType;

public class GranComponent implements Component {

    public boolean isAnimating;
    public PotType animatesType;

    public float elapsedTime;
}
