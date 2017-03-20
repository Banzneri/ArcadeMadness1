package com.mygdx.arcademadness;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Banzneri on 06/03/2017.
 */

public class Boy extends Character {

    public Boy(float x, float y, GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1f);
        setDirection(direction);
        // setRandomTexture();

        textureLeft = new Texture("jonne_left.png");
        textureRight = new Texture("jonne_right.png");
        textureDown = new Texture("jonne_front.png");
        textureUp = new Texture("jonne_back.png");

        int rand = MathUtils.random(2);

    }

    public void setRandomTexture() {
        int rand = MathUtils.random(0, 1);

        if(rand == 0) {
            textureLeft = new Texture("jonne_left.png");
            textureRight = new Texture("jonne_right.png");
            textureDown = new Texture("jonne_front.png");
            textureUp = new Texture("jonne_back.png");
        }

        if(rand == 1) {
            textureLeft = new Texture("jonna_left.png");
            textureRight = new Texture("jonna_right.png");
            textureDown = new Texture("jonna_front.png");
            textureUp = new Texture("jonna_back.png");
        }
    }
}
