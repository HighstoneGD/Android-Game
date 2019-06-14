package com.mygdx.game.component.marking;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.system.attack.potsystems.PotSystem;

public class PotComponent implements Component {

    public PotSystem system;
    public PotType type;
    public float progress;
    public float aimX;
    public float aimY;
}
