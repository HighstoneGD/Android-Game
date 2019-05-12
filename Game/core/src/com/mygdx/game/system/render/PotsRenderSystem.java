package com.mygdx.game.system.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.objects.BatchArguments;
import com.mygdx.game.common.objects.PotAnimator;

import java.util.ArrayList;
import java.util.List;

public class PotsRenderSystem extends EntitySystem {

    private static final Logger log = new Logger(PotsRenderSystem.class.getName(), Logger.DEBUG);
    private SpriteBatch batch;
    private Viewport viewport;
    private List<PotAnimator> animators;

    public PotsRenderSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
        animators = new ArrayList<PotAnimator>();
    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        batch.begin();

        draw();

        batch.end();
    }

    private void draw() {
//        log.debug("animators.size() = " + animators.size());

        for (PotAnimator animator : animators) {
            BatchArguments arguments = animator.getArgs();
            log.debug("width = " + arguments.width + ", height = " + arguments.height);

            batch.draw(arguments.texture, arguments.x, arguments.y, arguments.width, arguments.height);
        }
    }

    public void addAnimator(PotAnimator animator) {
        animators.add(animator);
    }

    public void removeAnimator(PotAnimator animator) {
        animators.remove(animator);
    }
}
