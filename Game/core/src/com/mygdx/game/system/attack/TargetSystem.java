package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Logger;

import java.util.Random;

public class TargetSystem extends EntitySystem {

    private static final Logger log = new Logger(TargetSystem.class.getName(), Logger.DEBUG);

    public int selectTargetCell() {
        Random random = new Random();
        int target = random.nextInt(25);
        log.debug("selected target " + target);

        return target;
    }
}
