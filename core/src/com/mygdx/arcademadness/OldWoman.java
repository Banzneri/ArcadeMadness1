package com.mygdx.arcademadness;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class OldWoman extends Character {

    public OldWoman(float x, float y, GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(2f);
        setAge(80f);
        setDirection(direction);

        textureLeft = new Texture("granny_left.png");
        textureRight = new Texture("granny_right.png");
        textureDown = new Texture("granny_front.png");
        textureUp = new Texture("granny_back.png");
    }
}
