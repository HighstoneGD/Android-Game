package com.mygdx.game.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.component.AnimationComponent;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.ShadowComponent;
import com.mygdx.game.component.SpeedComponent;
import com.mygdx.game.component.TextureComponent;
import com.mygdx.game.component.marking.CatComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.component.marking.GranComponent;
import com.mygdx.game.component.marking.PotComponent;

public class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<MovementStateComponent> MOVEMENT_STATE =
            ComponentMapper.getFor(MovementStateComponent.class);

    public static final ComponentMapper<PositionOnGridComponent> POSITION_ON_GRID =
            ComponentMapper.getFor(PositionOnGridComponent.class);

    public static final ComponentMapper<NumberComponent> NUMBER =
            ComponentMapper.getFor(NumberComponent.class);

    public static final ComponentMapper<AttackStateComponent> ATTACK_STATE =
            ComponentMapper.getFor(AttackStateComponent.class);

    public static final ComponentMapper<BonusComponent> BONUS =
            ComponentMapper.getFor(BonusComponent.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<PotComponent> POT_COMPONENT =
            ComponentMapper.getFor(PotComponent.class);

    public static final ComponentMapper<AnimationComponent> ANIMATION_COMPONENT =
            ComponentMapper.getFor(AnimationComponent.class);

    public static final ComponentMapper<CellComponent> CELL_COMPONENT =
            ComponentMapper.getFor(CellComponent.class);

    public static final ComponentMapper<SpeedComponent> SPEED =
            ComponentMapper.getFor(SpeedComponent.class);

    public static final ComponentMapper<ShadowComponent> SHADOW =
            ComponentMapper.getFor(ShadowComponent.class);

//    public static final ComponentMapper<CatComponent> CAT =
//            ComponentMapper.getFor(CatComponent.class);

    public static final ComponentMapper<GranComponent> GRAN =
            ComponentMapper.getFor(GranComponent.class);

    private Mappers() {
    }
}
