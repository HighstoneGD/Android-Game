package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.marking.PlayerComponent;

public class InfoSystem extends IteratingSystem {

    private static final Logger log = new Logger(InfoSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            PlayerComponent.class
    ).get();

    public InfoSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = Mappers.PLAYER.get(entity);
        log.debug("Player isAnimating = " + playerComponent.isAnimating + " player goesOnDirection = " + playerComponent.goesOnDirection);
    }
}
