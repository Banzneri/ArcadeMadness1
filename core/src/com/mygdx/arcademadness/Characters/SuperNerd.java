package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Banzneri on 27/04/2017.
 */

public class SuperNerd extends Character {

    public SuperNerd(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        super(x, y, host);
        setSpeed(1f);
        setAge(MathUtils.random(7, 40));
        setDirection(direction);

        textureLeft = new Texture("CharacterTextures/supernerd_left.png");
        textureRight = new Texture("CharacterTextures/supernerd_right.png");
        textureDown = new Texture("CharacterTextures/supernerd_front.png");
        textureUp = new Texture("CharacterTextures/supernerd_back.png");
    }

    @Override
    public void drawAge() {
        if(TimeUtils.timeSinceMillis(prevTime) < 2500) {
            host.getFontAge().setColor(Color.RED);
            host.getFontAge().draw(host.getHost().getBatch(), "MEGANERD", getRect().getX() - 50, getRect().getY() + getRect().getHeight() * 2);
        }
    }
}
