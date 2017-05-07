package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class OldWoman extends Character {

    public OldWoman(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(0.5f);
        setAge(MathUtils.random(70, 110));
        setDirection(direction);

        setTexture();
    }

    public void setTexture() {
        int rand = MathUtils.random(1, 2);

        textureLeft = new Texture("CharacterTextures/granny_" + rand + "_left.png");
        textureRight = new Texture("CharacterTextures/granny_" + rand + "_right.png");
        textureDown = new Texture("CharacterTextures/granny_" + rand + "_front.png");
        textureUp = new Texture("CharacterTextures/granny_" + rand + "_back.png");
    }
}
