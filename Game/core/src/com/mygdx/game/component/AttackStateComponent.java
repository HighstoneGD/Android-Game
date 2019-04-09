package com.mygdx.game.component;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.common.DamageObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttackStateComponent implements Component {
    public List<DamageObject> timers = new ArrayList<DamageObject>();
}
