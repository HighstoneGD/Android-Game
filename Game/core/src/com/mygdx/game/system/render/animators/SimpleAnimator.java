package com.mygdx.game.system.render.animators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.objects.BatchArguments;
import com.mygdx.game.common.objects.PotAnimator;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.render.PotsRenderSystem;

public class SimpleAnimator implements Runnable, PotAnimator {

    private static final Logger log = new Logger(SimpleAnimator.class.getName(), Logger.DEBUG);
    private Animation<TextureRegion> animation;
    private TextureAtlas simple;
    private AssetManager assetManager;
    private BasicGameScreen screen;

    private float duration;
    private float frames;

    private float elapsedTime;
    private float cellX;
    private float k;
    private float b;
    private float coef;
    private int yNumber;
    private int raws;

    public SimpleAnimator(BasicGameScreen screen, float cellX, float cellY, int yNumber) {
        this.assetManager = screen.getAssetManager();
        this.screen = screen;
        duration = Constants.SIMPLE_FLIGHT_TIME / 1000f;
        simple = assetManager.get(AssetDescriptors.SIMPLE_TEXTURE);
        frames = simple.getRegions().size;
        animation = new Animation<TextureRegion>(duration / frames, simple.getRegions());

        this.cellX = cellX;
        this.yNumber = yNumber;
        raws = screen.y;

        elapsedTime = 0;
        k = AnimatorUtils.calculateK(cellX, cellY);
        b = AnimatorUtils.calculateB(k);
        coef = AnimatorUtils.getDimensionCoefficient(yNumber + 1, raws);

        screen.getEngine().getSystem(PotsRenderSystem.class).addAnimator(this);
    }

    @Override
    public void run() {
        while (elapsedTime <= (Constants.SIMPLE_FLIGHT_TIME / 1000)) {
            elapsedTime += Gdx.graphics.getDeltaTime();
        }

        screen.getEngine().getSystem(PotsRenderSystem.class).removeAnimator(this);
    }

    @Override
    public BatchArguments getArgs() {
        float x = AnimatorUtils.getX(duration, elapsedTime, cellX);
        float y = AnimatorUtils.getY(k, b, x);
        float width = AnimatorUtils.getWidth(coef, duration, elapsedTime);
        float height = AnimatorUtils.getHeight(coef, duration, elapsedTime);
        TextureRegion texture = animation.getKeyFrame(elapsedTime, false);

        BatchArguments arguments = new BatchArguments(texture, x, y, width, height);
        return arguments;
    }
}
