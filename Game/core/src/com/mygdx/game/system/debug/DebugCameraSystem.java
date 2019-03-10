package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.util.debug.DebugCameraController;


/**
 * Created by goran on 5/09/2016.
 */
public class DebugCameraSystem extends EntitySystem {

    // == constants ==
    private static final DebugCameraController DEBUG_CAMERA_CONTROLLER = new DebugCameraController();

    // == attributes ==
    private final OrthographicCamera camera;

    // == constructors ==
    public DebugCameraSystem(OrthographicCamera camera, float startX, float startY) {
        this.camera = camera;
        DEBUG_CAMERA_CONTROLLER.setStartPosition(startX, startY);
    }

    // == update ==
    @Override
    public void update(float deltaTime) {
        DEBUG_CAMERA_CONTROLLER.handleDebugInput(deltaTime);
        DEBUG_CAMERA_CONTROLLER.applyTo(camera);
    }
}
