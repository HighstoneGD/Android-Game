package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.component.marking.PlayerComponent;
import com.mygdx.game.controlling.GameManager;

public class InfoSystem extends IntervalSystem {

    private static final Logger log = new Logger(InfoSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            PlayerComponent.class
    ).get();

    public InfoSystem() {
        super(5f);
    }

    @Override
    protected void updateInterval() {
        log.debug("score = " + GameManager.INSTANCE.getScore());
    }
}
