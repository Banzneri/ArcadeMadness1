package com.mygdx.arcademadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Banzneri on 21/02/2017.
 */

/**
 *
 */
public class Woman extends Character {

    public Woman(float x, float y, ArcadeMadness host, String direction) {
        super(x, y, host);
        setSpeed(1/2f);
        setDirection(direction);

        textureLeft = new Texture("potatowoman_left.png");
        textureRight = new Texture("potatowoman.png");
        textureDown = new Texture("potatowoman_left.png");
        textureUp = new Texture("potatowoman.png");
    }
}
