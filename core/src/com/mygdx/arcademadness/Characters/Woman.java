package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Banzneri on 21/02/2017.
 */

public class Woman extends Character {

    public Woman(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1f);
        setAge(30);
        setDirection(direction);

        textureLeft = new Texture("adult_left.png");
        textureRight = new Texture("adult_right.png");
        textureDown = new Texture("adult_front.png");
        textureUp = new Texture("adult_back.png");
    }
}
