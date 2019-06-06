package com.mygdx.game.util.objects;

public class DamageObject {

    public int damage;
    public float time;

    public DamageObject(int damage, float time) {
        this.damage = damage;
        this.time = time;
    }

    public void reduceTimer(float deltaTime) {
        time -= deltaTime;

        if (time < 0) {
            time = 0;
        }
    }
}
