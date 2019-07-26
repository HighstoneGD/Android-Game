package com.mygdx.game.util.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.enums.PotType;

public class Tutorial extends Image {

    private TextureRegion dropImage;
    private TextureRegion smashImage;

    private float timer;

    public Tutorial(PotType type, TextureAtlas tutorials) {
        super(new TextureRegionDrawable());
        initImages(type, tutorials);
        ((TextureRegionDrawable) getDrawable()).setRegion(dropImage);
        resetTimer();
    }
    
    private void initImages(PotType type, TextureAtlas tutorials) {
        if (type == PotType.SIMPLE) {
            dropImage = tutorials.findRegion(RegionNames.SIMPLE_FLIGHT);
            smashImage = tutorials.findRegion(RegionNames.SIMPLE_SMASH);
        } else if (type == PotType.LARGE) {
            dropImage = tutorials.findRegion(RegionNames.LARGE_FLIGHT);
            smashImage = tutorials.findRegion(RegionNames.LARGE_SMASH);
        } else if (type == PotType.BONUS) {
            dropImage = tutorials.findRegion(RegionNames.BONUS_FLIGHT);
            smashImage = tutorials.findRegion(RegionNames.BONUS_SMASH);
        } else if (type == PotType.EXPLOSIVE) {
            dropImage = tutorials.findRegion(RegionNames.EXPLOSIVE_FLIGHT);
            smashImage = tutorials.findRegion(RegionNames.EXPLOSIVE_SMASH);
        } else if (type == PotType.IRON) {
            dropImage = tutorials.findRegion(RegionNames.IRON_FLIGHT);
            smashImage = tutorials.findRegion(RegionNames.IRON_SMASH);
        }
    }

    @Override
    public void act(float delta) {
//        super.act(delta);
        timer -= delta;

        if (timer <= 0) {
            changeImage();
            resetTimer();
        }
    }

    private void changeImage() {
        if (((TextureRegionDrawable) getDrawable()).getRegion() == dropImage) {
            ((TextureRegionDrawable) getDrawable()).setRegion(smashImage);
        } else {
            ((TextureRegionDrawable) getDrawable()).setRegion(dropImage);
        }
    }

    private void resetTimer() {
        timer = 1.3f;
    }
}
