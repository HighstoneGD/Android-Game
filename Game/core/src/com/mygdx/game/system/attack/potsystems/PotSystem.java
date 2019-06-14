package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.util.logic.NumberConverter;

public abstract class PotSystem extends EntitySystem {

    private static final Family CELLS = Family.all(
            PositionOnGridComponent.class,
            AttackStateComponent.class
    ).get();

    private static final Logger log = new Logger(PotSystem.class.getName(), Logger.DEBUG);

    protected final BasicGameScreen screen;
    protected final PooledEngine engine;
    private final EntityFactory factory;

    protected final int x;
    protected final int y;

    protected ImmutableArray<Entity> cells;

    protected PotType type;
    protected float potExistenceTime;

    protected float cellX;
    protected float cellY;

    private float throwTimer;
    private boolean isThrown;

    public PotSystem(BasicGameScreen screen, PotType type, int x, int y) {
        this.screen = screen;
        this.engine = screen.getEngine();
        this.factory = screen.getFactory();
        this.type = type;
        this.x = x;
        this.y = y;

        initPotExistenceTime();

        isThrown = false;
        throwTimer = GameData.POT_THROW_TIME;
    }

    @Override
    public void update(float deltaTime) {
        if (isThrown) {
            throwTimer -= deltaTime;
            checkThrowTimer();
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        throwPot(type);
    }

    private void throwPot(PotType type) {
        engine.getSystem(GranRenderSystem.class).throwPot(type);
        startThrowTimer();
    }

    private void startThrowTimer() {
        isThrown = true;
    }

    private void checkThrowTimer() {
        if (throwTimer <= 0) {
            isThrown = false;
            cells = engine.getEntitiesFor(CELLS);
            initCellCoordinates();
            factory.addPot(type, cellX, cellY, x, y, this);
        }
    }

    protected void initPotExistenceTime() {
        potExistenceTime = GameData.POT_EXISTENCE_TIME;
    }

    protected void initCellCoordinates() {
        cellX = engine.getSystem(NumberConverter.class).getCoordinates(x, y).x;
        cellY = engine.getSystem(NumberConverter.class).getCoordinates(x, y).y;
    }

    public abstract void attack();
}
