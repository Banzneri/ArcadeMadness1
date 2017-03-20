package com.mygdx.arcademadness;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Banzneri on 22/02/2017.
 */

public class Entrance {
    private float x;
    private float y;
    private String direction;
    private Rectangle rect;
    private GameScreen host;
    private float width = ArcadeMadness.TILE_SIZE_IN_PIXELS;
    private float height = ArcadeMadness.TILE_SIZE_IN_PIXELS;

    public Entrance(float x, float y, GameScreen host, String direction) {
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
        for(Character character : host.getCharacterList()) {
            if(character.getRect().overlaps(rect)) {
                return false;
            }
        }
        return true;
    }
}
