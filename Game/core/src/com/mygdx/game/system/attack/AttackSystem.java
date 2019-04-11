package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.CellComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.debug.GameConfig;
import com.mygdx.game.debug.PotType;
import com.mygdx.game.screen.game.EndlessModeScreen;
import com.mygdx.game.system.TimerSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AttackSystem extends IntervalSystem {

    private static final Logger log = new Logger(AttackSystem.class.getName(), Logger.DEBUG);
    private EndlessModeScreen screen;
    private Map<PotType, Integer> priorities;
    private Random random;

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            PositionOnGridComponent.class
    ).get();

    private static final Family CELLS = Family.all(
            CellComponent.class,
            NumberComponent.class,
            AttackStateComponent.class
    ).get();

    public AttackSystem(float attackSpeed, EndlessModeScreen screen) {
        super(attackSpeed);
        this.screen = screen;
        priorities = new HashMap<PotType, Integer>();
        priorities.put(PotType.SIMPLE, 0);
        priorities.put(PotType.LARGE, 1);
        priorities.put(PotType.IRON, 2);
        priorities.put(PotType.CAT, 3);
        priorities.put(PotType.EXPLOSIVE, 4);
        random = new Random();
    }

    @Override
    protected void updateInterval() {
        PotType type = selectPotType();
        int x = selectTargetX();
        int y = selectTargetY();

        if (type == PotType.SIMPLE) {
            getEngine().getSystem(SimplePotSystem.class).attack(x, y);
        } else if (type == PotType.LARGE) {
            getEngine().getSystem(LargePotSystem.class).attack(x, y);
        } else if (type == PotType.EXPLOSIVE) {
            getEngine().getSystem(ExplosivePotSystem.class).attack(x, y);
        } else if (type == PotType.IRON) {
            getEngine().getSystem(IronPotSystem.class).attack(x, y);
        }
    }

    private PotType selectPotType() {
        PotType type = PotType.SIMPLE;

        for (PotType type1 : priorities.keySet()) {
            if (priorities.get(type) < priorities.get(type1) && GameManager.INSTANCE.getCooldown(type1) == 0) {
                type = type1;
            }
        }

        log.debug("type = " + type);
        GameManager.INSTANCE.decrementCooldowns();
        GameManager.INSTANCE.resetCooldown(type);
        return type;
    }

    private int selectTargetX() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);

        for (Entity entity : player) {
            PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);
            int k = random.nextInt(3);
            if (!(position.xNumber + 1 - k < 0) && !(position.xNumber + 1 - k > screen.x - 1)) {
                log.debug("x = " + (position.xNumber + 1 - k));
                return position.xNumber + 1 - k;
            }
        }

        int pos = random.nextInt(screen.x);
        log.debug("x = " + pos);
        return pos;
    }

    private int selectTargetY() {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);

        for (Entity entity : player) {
            PositionOnGridComponent position = Mappers.POSITION_ON_GRID.get(entity);
            int k = random.nextInt(3);
            if (!(position.yNumber + 1 - k < 0) && !(position.yNumber + 1 - k > screen.y - 1)) {
                log.debug("y = " + (position.yNumber + 1 - k));
                return position.yNumber + 1 - k;
            }
        }

        int pos = random.nextInt(screen.y);
        log.debug("y = " + pos);
        return pos;
    }
}
