package com.mygdx.game.system.debug;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.screen.game.LevelsScreen;

public class InfoSystem extends IntervalSystem {

    private static final Logger log = new Logger(InfoSystem.class.getName(), Logger.DEBUG);

    private LevelsScreen screen;

    public InfoSystem(LevelsScreen screen) {
        super(5f);
        this.screen = screen;
    }

    @Override
    protected void updateInterval() {
        log.debug("pot left = " + screen.getPotsLeft());
    }
}
