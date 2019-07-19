package com.mygdx.game.screen.game;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.system.ScoreInTimeSystem;
import com.mygdx.game.system.attack.AttackSystem;

import java.util.ArrayList;
import java.util.List;

public class EndlessModeScreen extends BasicGameScreen {

    private static final Logger log = new Logger(EndlessModeScreen.class.getName(), Logger.DEBUG);

    private List<PotType> potTypes;

    public EndlessModeScreen(AndroidGame game) {
        super(game, 5, 5);
        initPotTypes();
    }

    @Override
    protected void resetHealthManager() {
        HealthManager.reset();
    }

    @Override
    protected void initPotSpawnSpeed() {
        potSpawnSpeed = GameData.DEFAULT_POT_SPAWN_SPEED;
    }

    @Override
    protected void initSystems() {
        super.initSystems();
        createScoreSystem();
    }

    @Override
    protected void createAttackAndBonusSystems() {
        engine.addSystem(new AttackSystem(potSpawnSpeed, this, potTypes));
        super.createAttackAndBonusSystems();
    }

    private void createScoreSystem() {
        engine.addSystem(new ScoreInTimeSystem());
    }

    @Override
    public void potThrown() {

    }

    @Override
    protected void gameOverAction() {
        game.getScoreManager().addScore((long) GameManager.INSTANCE.getScore());
    }

    private void initPotTypes() {
        potTypes = new ArrayList<>();
        potTypes.add(PotType.SIMPLE);
        potTypes.add(PotType.LARGE);
        potTypes.add(PotType.BONUS);
        potTypes.add(PotType.EXPLOSIVE);
        potTypes.add(PotType.IRON);
    }
}
