package com.mygdx.game.component;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.common.objects.Bonus;

import java.util.ArrayList;
import java.util.List;

public class BonusComponent implements Component {
    public List<Bonus> timers = new ArrayList<Bonus>();
}
