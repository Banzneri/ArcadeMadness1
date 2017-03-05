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
public class Monster extends Character {

    public Monster(float x, float y, ArcadeMadness host, String direction) {
        super(x, y, host);
        setSpeed(1/2f);
        setDirection(direction);

        textureLeft = new Texture("monster.png");
        textureRight = new Texture("monster.png");
        textureDown = new Texture("monster.png");
        textureUp = new Texture("monster.png");
    }
}
