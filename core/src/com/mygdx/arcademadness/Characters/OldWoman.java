package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class OldWoman extends Character {

    public OldWoman(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(2f);
        setAge(80);
        setDirection(direction);

        textureLeft = new Texture("granny_left.png");
        textureRight = new Texture("granny_right.png");
        textureDown = new Texture("granny_front.png");
        textureUp = new Texture("granny_back.png");
    }
}
