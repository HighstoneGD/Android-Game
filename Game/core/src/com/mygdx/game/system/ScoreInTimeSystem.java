package com.mygdx.game.system;

import com.badlogic.ashley.core.EntitySystem;
import com.mygdx.game.controlling.GameManager;

public class ScoreInTimeSystem extends EntitySystem {

    @Override
    public void update(float deltaTime) {
        GameManager.INSTANCE.updateScore(deltaTime);
    }
}
