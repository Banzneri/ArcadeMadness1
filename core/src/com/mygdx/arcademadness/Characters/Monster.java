package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Banzneri on 21/02/2017.
 */

/**
 *
 */
public class Monster extends Character {
    private int timeToLiveInSeconds = 20;

    public Monster(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1/2f);
        setAge(MathUtils.random(200, 1000));
        setDirection(direction);

        textureLeft = new Texture("CharacterTextures/ghost_left.png");
        textureRight = new Texture("CharacterTextures/ghost_right.png");
        textureDown = new Texture("CharacterTextures/ghost_front.png");
        textureUp = new Texture("CharacterTextures/ghost_back.png");
    }

    public boolean hasExpired() {
        return getTimeInPlay() > timeToLiveInSeconds;
    }
}
