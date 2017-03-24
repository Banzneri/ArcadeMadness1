package com.mygdx.arcademadness.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 22/02/2017.
 */

public class Entrance {
    private float x;
    private float y;
    private String direction;
    private Rectangle rect;
    private com.mygdx.arcademadness.Screens.GameScreen host;
    private float width = ArcadeMadness.TILE_SIZE_IN_PIXELS;
    private float height = ArcadeMadness.TILE_SIZE_IN_PIXELS;

    public Entrance(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host, String direction) {
        this.x = x;
        this.y = y;
        this.host = host;
        this.direction = direction;
        rect = new Rectangle(x, y, width, height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getDirection() {
        return direction;
    }

    public boolean isFree() {
        for(com.mygdx.arcademadness.Characters.Character character : host.getCharacterList()) {
            if(character.getRect().overlaps(rect)) {
                return false;
            }
        }
        return true;
    }
}
