package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Banzneri on 06/03/2017.
 */

public class Boy extends Character {

    public Boy(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1f);
        setAge(MathUtils.random(16, 17));
        setDirection(direction);
        setTexture();
    }

    public void setTexture() {
        int rand = MathUtils.random(1, 4);

        textureLeft = new Texture("CharacterTextures/jonne_" + rand + "_left.png");
        textureRight = new Texture("CharacterTextures/jonne_" + rand + "_right.png");
        textureDown = new Texture("CharacterTextures/jonne_" + rand + "_front.png");
        textureUp = new Texture("CharacterTextures/jonne_" + rand + "_back.png");
    }
}
