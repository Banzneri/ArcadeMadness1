package com.mygdx.arcademadness.GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Banzneri on 16/03/2017.
 */

/**
 * The class for the game rooms.
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

    /**
     * Constructor for the GameRoom class. Receives as parameters position x/y, width, height,
     * age restriction as a string (from TiledMap rectangle name) and a GameScreen host object
     *
     * @param x coordinate x of the room
     * @param y coordinate y of the room
     * @param width the width of the room
     * @param height the height of the room
     * @param ageRestriction the age restriction of the rrom
     * @param host the GameScreen host object
     */
    public GameRoom(float x, float y, float width, float height, String ageRestriction, com.mygdx.arcademadness.Screens.GameScreen host) {
        this.x = x;
        this.y = y;
        this.host = host;
        this.width = width;
        this.height = height;
        initAgeRestriction(ageRestriction);

        numberOfPeople = 0;
    }

    /**
     * Sets the ageRestriction according to a string (taken from TiledMap rectangle name) given to the object
     *
     * @param age the age restriction as a string (taken from TiledMap rectangle object name)
     */
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

    /**
     * Setter for the rectangle where the number of people is drawn
     *
     * @param numberRectangle rectangle for the area where the number of people is drawn
     */
    public void setNumberRectangle(Rectangle numberRectangle) {
        this.numberRectangle = numberRectangle;
    }

    /**
     * Setter for the ageRestriction integer
     *
     * @param ageRestriction the age restriction of the room as integer
     */
    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    /**
     * Setter for the x coordinate
     *
     * @return the x coordinate of the room
     */
    public float getX() {
        return x;
    }

    /**
     * Setter for the y coordinate
     *
     * @return the y coordinate of the room
     */
    public float getY() {
        return y;
    }

    /**
     * Getter for the number of people in the room
     *
     * @return the number of people in the room
     */
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * Getter for the ageRestriction of the room
     *
     * @return the age restriction of the room
     */
    public int getAgeRestriction() {
        return ageRestriction;
    }

    /**
     * Increments the number of people by one
     */
    public void addCharacter() {
        numberOfPeople++;
    }

    /**
     * Draws the number of people in the room
     */
    public void drawNumberOfPeople() {
        host.getFont().setColor(Color.WHITE);
        host.getFont().draw(host.getHost().getBatch(), Integer.toString(numberOfPeople) + "/4", numberRectangle.getX(), numberRectangle.getY() + numberRectangle.getHeight() / 2);
    }

    /**
     * Sets the number of people to 0, used when a monster enters the room
     */
    public void setNumberOfPeopleToZero() {
        numberOfPeople = 0;
    }

    /**
     * If number of people is less than 4, sets number of people to 4, else adds 3 to number of people
     * Used when a super nerd enters a room
     */
    public void fillRoom() {
        if(this.numberOfPeople < 4) {
            numberOfPeople = 4;
        } else {
            numberOfPeople += 3;
        }
    }
}
