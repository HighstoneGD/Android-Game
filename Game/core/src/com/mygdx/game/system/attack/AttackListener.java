package com.mygdx.game.system.attack;

import com.mygdx.game.debug.PotType;

public interface AttackListener {
    void attack(PotType type, int cell);
}
