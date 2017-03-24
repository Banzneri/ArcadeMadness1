package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Banzneri on 21/02/2017.
 */

/**
 *
 */
public class Monster extends Character {

    public Monster(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1/2f);
        setAge(30);
        setDirection(direction);

        textureLeft = new Texture("monster_left.png");
        textureRight = new Texture("monster_right.png");
        textureDown = new Texture("monster_front.png");
        textureUp = new Texture("monster_back.png");
    }
}
