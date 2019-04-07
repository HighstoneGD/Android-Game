package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.EntitySystem;
import com.mygdx.game.debug.PotType;

import java.util.HashMap;
import java.util.Map;

public class SelectPotSystem extends EntitySystem {

    private Map<PotType, Integer> priorities;

    public SelectPotSystem() {
        priorities = new HashMap<PotType, Integer>();
        priorities.put(PotType.SIMPLE, 0);
        priorities.put(PotType.LARGE, 1);
        priorities.put(PotType.IRON, 2);
        priorities.put(PotType.CAT, 3);
        priorities.put(PotType.EXPLOSIVE, 4);
    }

    public PotType selectPotType() {

    }
}
