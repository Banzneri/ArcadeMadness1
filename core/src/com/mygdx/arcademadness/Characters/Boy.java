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
        setAge(16);
        setDirection(direction);
        setRandomTexture();
    }

    public void setRandomTexture() {
        int rand = MathUtils.random(0, 1);

        if(rand == 0) {
            textureLeft = new Texture("CharacterTextures/jonne_left.png");
            textureRight = new Texture("CharacterTextures/jonne_right.png");
            textureDown = new Texture("CharacterTextures/jonne_front.png");
            textureUp = new Texture("CharacterTextures/jonne_back.png");
        } else {
            textureLeft = new Texture("CharacterTextures/jonna_left.png");
            textureRight = new Texture("CharacterTextures/jonna_right.png");
            textureDown = new Texture("CharacterTextures/jonne_front.png");
            textureUp = new Texture("CharacterTextures/jonna_back.png");
        }
    }
}
