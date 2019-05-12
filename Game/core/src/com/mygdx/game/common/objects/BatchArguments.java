package com.mygdx.game.common.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BatchArguments {
    public TextureRegion texture;
    public float x;
    public float y;
    public float width;
    public float height;

    public BatchArguments(TextureRegion texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
