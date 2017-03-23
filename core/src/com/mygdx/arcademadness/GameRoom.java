package com.mygdx.arcademadness;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class GameRoom {
    private int numberOfPeople;
    private float x;
    private float y;
    private float width;
    private float height;
    private GameScreen host;

    public GameRoom(float x, float y, float width, float height, GameScreen host) {
        this.x = x;
        this.y = y;
        this.host = host;
        this.width = width;
        this.height = height;

        numberOfPeople = 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void addCharacter() {
        numberOfPeople++;
    }

    public void drawNumberOfPeople() {
        host.getFont().draw(host.getHost().getBatch(), Integer.toString(numberOfPeople), x + width / 2 - 8, y + height / 2 - 16);
    }
}
