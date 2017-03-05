package com.mygdx.arcademadness;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Banzneri on 22/02/2017.
 */

public class Arrow {
    private float x;
    private float y;
    private float width = ArcadeMadness.TILE_SIZE_IN_PIXELS;
    private float height = ArcadeMadness.TILE_SIZE_IN_PIXELS;

    private TextureRegion upArrowTexture;
    private TextureRegion downArrowTexture;
    private TextureRegion leftArrowTexture;
    private TextureRegion rightArrowTexture;

    private String direction;
    private Rectangle rect;

    public Arrow(String direction, float x, float y) {
        this.x = x;
        this.y = y;
        rect = new Rectangle(this.x, this.y, width, height);
        this.direction = direction;

        Texture arrowSheet = new Texture("arrows.png");

        downArrowTexture = new TextureRegion(arrowSheet, 0, 0, 32, 32);
        rightArrowTexture = new TextureRegion(arrowSheet, 32, 0, 32, 32);
        upArrowTexture = new TextureRegion(arrowSheet, 0, 32, 32, 32);
        leftArrowTexture = new TextureRegion(arrowSheet, 32, 32, 32, 32);
    }

    public void draw(SpriteBatch batch) {
        if(direction.equals("down")) {
            batch.draw(downArrowTexture, x, y, width, height);
        } else if(direction.equals("up")) {
            batch.draw(upArrowTexture, x, y, width, height);
        } else if(direction.equals("left")) {
            batch.draw(leftArrowTexture, x, y, width, height);
        } else {
            batch.draw(rightArrowTexture, x, y, width, height);
        }
    }

    public Rectangle getRectangle() {
        return rect;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
