package com.mygdx.game.common.objects;

public class Shadow {

    public float x;
    public float y;
    public float width;
    public float height;

    public Shadow(float x, float y, float height) {
        this.x = x;
        this.y = y;
        this.width = height * 2f;
        this.height = height;
    }
}
