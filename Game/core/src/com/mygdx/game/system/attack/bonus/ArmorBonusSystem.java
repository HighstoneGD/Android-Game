package com.mygdx.game.system.attack.bonus;

import com.mygdx.game.controlling.HealthManager;

public class ArmorBonusSystem extends BonusSystem {

    public void picked() {
        HealthManager.pickArmor();
    }

}
