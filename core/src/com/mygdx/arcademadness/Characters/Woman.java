package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Banzneri on 21/02/2017.
 */

public class Woman extends Character {

    public Woman(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1f);
        setAge(MathUtils.random(16, 45));
        setDirection(direction);

        setTexture();
    }

    public void setTexture() {
        int rand = MathUtils.random(1, 3);

        textureLeft = new Texture("CharacterTextures/adult_" + rand + "_left.png");
        textureRight = new Texture("CharacterTextures/adult_" + rand + "_right.png");
        textureDown = new Texture("CharacterTextures/adult_" + rand + "_front.png");
        textureUp = new Texture("CharacterTextures/adult_" + rand + "_back.png");
    }
}
