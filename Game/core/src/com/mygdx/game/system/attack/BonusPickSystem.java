package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.system.attack.bonus.ArmorBonusSystem;
import com.mygdx.game.system.attack.bonus.LifeBonusSystem;
import com.mygdx.game.system.attack.bonus.SpeedUpBonusSystem;
import com.mygdx.game.util.objects.Bonus;

public class BonusPickSystem extends IteratingSystem {

    private static final Family CELLS = Family.all(
            BonusComponent.class,
            CellComponent.class
    ).get();

    public BonusPickSystem() {
        super(CELLS);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CellComponent cellComponent = Mappers.CELL_COMPONENT.get(entity);
        if (cellComponent.hasPlayer) {
            BonusComponent bonusComponent = Mappers.BONUS.get(entity);

            for (int i = 0; i < bonusComponent.timers.size(); i++) {
                Bonus bonus = bonusComponent.timers.get(i);

                if (bonus.type == BonusType.LIFE) {
                    getEngine().getSystem(LifeBonusSystem.class).picked();
                } else if (bonus.type == BonusType.ARMOR) {
                    getEngine().getSystem(ArmorBonusSystem.class).picked();
                } else if (bonus.type == BonusType.SPEED_UP) {
                    getEngine().getSystem(SpeedUpBonusSystem.class).picked();
                }

            }

            bonusComponent.timers.clear();
            return;
        }
    }
}
