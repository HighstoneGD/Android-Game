package com.mygdx.game.component;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.util.objects.DamageObject;

import java.util.ArrayList;
import java.util.List;

public class AttackStateComponent implements Component {
    public List<DamageObject> timers = new ArrayList<DamageObject>();
}
