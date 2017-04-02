package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class Child extends Character {

    public Child(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1f);
        setAge(7);
        setDirection(direction);

        textureLeft = new Texture("child_left.png");
        textureRight = new Texture("child_right.png");
        textureDown = new Texture("child_front.png");
        textureUp = new Texture("child_back.png");

    }
}
