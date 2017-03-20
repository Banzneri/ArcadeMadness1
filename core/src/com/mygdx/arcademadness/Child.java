package com.mygdx.arcademadness;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class Child extends Character {

    public Child(float x, float y, GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1f);
        setDirection(direction);

        textureLeft = new Texture("child_left.png");
        textureRight = new Texture("child_right.png");
        textureDown = new Texture("child_front.png");
        textureUp = new Texture("child_back.png");

    }
}
