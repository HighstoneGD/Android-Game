package com.mygdx.game.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PlayerComponent;

public class TimerSystem extends EntitySystem {

    private static final Family FAMILY = Family.all(
            PlayerComponent.class
    ).get();

    public static void startTimer() {
    }

    private static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
