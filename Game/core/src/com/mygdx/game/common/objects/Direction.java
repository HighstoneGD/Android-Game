package com.mygdx.game.common.objects;

import java.util.Random;

public class Direction {

    public Directions direction;
    private Random random;

    public Direction() {
        random = new Random();
        int k = random.nextInt(2);

        if (k == 0) {
            direction = Directions.LEFT;
        } else {
            direction = Directions.RIGHT;
        }
    }

    public void changeDirection() {

        if (direction == Directions.LEFT) {
            direction = Directions.RIGHT;
        } else if (direction == Directions.RIGHT) {
            direction = Directions.LEFT;
        }

    }
}
