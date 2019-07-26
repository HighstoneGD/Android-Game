package com.mygdx.game.common.levels;

import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.PotType;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private float potSpawnSpeed;
    private List<PotType> potTypes;
    private int x;
    private int y;
    private int potsAmount;
    private int lives;

    public Level(int number) {
        initPotSpawnSpeed(number);
        initPotTypes(number);
        initGridSize(number);

        potsAmount = GameData.POTS_AMOUNT_FIRST;

        if (number > 3) {
            potsAmount = GameData.POTS_AMOUNT_SECOND;
        }

        lives = GameData.START_LIVES_AMOUNT;

        if (number < 5) {
            lives--;
        }
    }

    private void initPotSpawnSpeed(int number) {
        potSpawnSpeed = GameData.DEFAULT_POT_SPAWN_SPEED;

        if (number == 1 || number == 7) {
            potSpawnSpeed += 0.2f;
        }
    }

    private void initPotTypes(int number) {
        potTypes = new ArrayList<>();
        potTypes.add(PotType.SIMPLE);

        if (number >= 3) {
            potTypes.add(PotType.LARGE);
        }

        if (number >= 4) {
            potTypes.add(PotType.BONUS);
        }

        if (number >= 5) {
            potTypes.add(PotType.EXPLOSIVE);
        }

        if (number >= 6) {
            potTypes.add(PotType.IRON);
        }
    }

    private void initGridSize(int number) {
        x = 5;
        y = 5;

        if (number >= 7) {
            x = 4;
            y = 4;
        }
    }

    public float getPotSpawnSpeed() {
        return potSpawnSpeed;
    }

    public List<PotType> getPotTypes() {
        return potTypes;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPotsAmount() {
        return potsAmount;
    }

    public int getLives() {
        return lives;
    }
}
