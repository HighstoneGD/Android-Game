package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.SpeedComponent;
import com.mygdx.game.component.marking.PotComponent;
import com.mygdx.game.screen.game.BasicGameScreen;

public class DropPotsSystem extends IteratingSystem {

    private static final Family POTS = Family.all(
            PositionComponent.class,
            PotComponent.class
    ).get();

    private static final Logger log = new Logger(DropPotsSystem.class.getName(), Logger.DEBUG);

    private BasicGameScreen screen;

    public DropPotsSystem(BasicGameScreen screen) {
        super(POTS);
        this.screen = screen;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        SpeedComponent speed = Mappers.SPEED.get(entity);
        PotComponent potComponent = Mappers.POT_COMPONENT.get(entity);

        try {
            position.y -= deltaTime * speed.speedY;
        } catch (NullPointerException e) {}

        if (potComponent.progress >= 1f) {
            smash(entity);
        }
    }

    private void smash(Entity entity) {
        PotComponent potComponent = Mappers.POT_COMPONENT.get(entity);
        screen.getFactory().addSmash(potComponent.type, potComponent.aimX, potComponent.aimY);
        potComponent.system.attack();
        getEngine().removeEntity(entity);
    }
}
