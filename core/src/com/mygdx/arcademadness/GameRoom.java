package com.mygdx.arcademadness;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class GameRoom {
    private float numberOfPeople;
    private float x;
    private float y;
    private float width;
    private float height;
    private GameScreen host;

    public GameRoom(float x, float y, float width, float height, GameScreen host) {
        this.x = x;
        this.y = y;
        this.host = host;

        numberOfPeople = 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void addCharacter() {
        numberOfPeople++;
    }

    public void drawNumberOfPeople() {
        host.getFont().draw(host.getHost().getBatch(), Float.toString(numberOfPeople), x + width / 2, y + height / 2);
    }
}
