package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Ellipse;

public abstract class GameObjectBase {

    private float x;
    private float y;
    private float width = 1;
    private float height = 1;

    private Ellipse bounds;

    public GameObjectBase(float width, float height) {
        bounds = new Ellipse(x, y, width, height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void updateBounds() {
        float halfWidth = width / 2f;
        float halfHeight = height / 2f;
        bounds.setPosition(x + halfWidth, y + halfHeight);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Ellipse getBounds() {
        return bounds;
    }
}
