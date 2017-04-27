package com.mygdx.arcademadness.GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class GameRoom {
    private int numberOfPeople;
    private float x;
    private float y;
    private float width;
    private float height;
    private int ageRestriction;
    private Rectangle numberRectangle;
    private com.mygdx.arcademadness.Screens.GameScreen host;

    public GameRoom(float x, float y, float width, float height, String ageRestriction, com.mygdx.arcademadness.Screens.GameScreen host) {
        this.x = x;
        this.y = y;
        this.host = host;
        this.width = width;
        this.height = height;
        initAgeRestriction(ageRestriction);

        numberOfPeople = 0;
    }

    public void initAgeRestriction(String age) {
        if(age.equals("3")) {
            setAgeRestriction(3);
        } else if(age.equals("7")) {
            setAgeRestriction(7);
        } else if(age.equals("16")) {
            setAgeRestriction(16);
        } else {
            setAgeRestriction(18);
        }
    }

    public void setNumberRectangle(Rectangle numberRectangle) {
        this.numberRectangle = numberRectangle;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
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

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void addCharacter() {
        numberOfPeople++;
    }

    public void drawNumberOfPeople() {
        host.getFont().setColor(Color.WHITE);
        host.getFont().draw(host.getHost().getBatch(), Integer.toString(numberOfPeople) + "/4", numberRectangle.getX(), numberRectangle.getY() + numberRectangle.getHeight() / 2);
    }

    public void setNumberOfPeopleToZero() {
        numberOfPeople = 0;
    }

    public void fillRoom() {
        if(this.numberOfPeople < 4) {
            numberOfPeople = 4;
        } else {
            numberOfPeople += 3;
        }
    }
}
