package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class Child extends Character {

    public Child(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1f);
        setAge(MathUtils.random(3, 12));
        setDirection(direction);

        setTexture();

    }

    public void setTexture() {
        int rand = MathUtils.random(1, 4);

        textureLeft = new Texture("CharacterTextures/child_" + rand + "_left.png");
        textureRight = new Texture("CharacterTextures/child_" + rand + "_right.png");
        textureDown = new Texture("CharacterTextures/child_" + rand + "_front.png");
        textureUp = new Texture("CharacterTextures/child_" + rand + "_back.png");
    }
}
