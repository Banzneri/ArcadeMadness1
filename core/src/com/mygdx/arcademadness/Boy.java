package com.mygdx.arcademadness;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Banzneri on 06/03/2017.
 */

public class Boy extends Character {

    public Boy(float x, float y, ArcadeMadness host, String direction) {
        super(x, y, host);
        setSpeed(1/2f);
        setDirection(direction);

        textureLeft = new Texture("jonne_left.png");
        textureRight = new Texture("jonne_right.png");
        textureDown = new Texture("jonne_front.png");
        textureUp = new Texture("jonne_back.png");
    }
}
