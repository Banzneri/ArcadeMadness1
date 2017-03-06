package com.mygdx.arcademadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Banzneri on 21/02/2017.
 */

public class Woman extends Character {

    public Woman(float x, float y, ArcadeMadness host, String direction) {
        super(x, y, host);
        setSpeed(1/2f);
        setDirection(direction);

        textureLeft = new Texture("adult_left.png");
        textureRight = new Texture("adult_right.png");
        textureDown = new Texture("adult_front.png");
        textureUp = new Texture("adult_back.png");
    }
}
