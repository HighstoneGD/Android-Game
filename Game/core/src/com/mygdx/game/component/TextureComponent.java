package com.mygdx.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureComponent implements Component {
    public boolean render;
    public TextureAtlas.AtlasRegion region;
}
