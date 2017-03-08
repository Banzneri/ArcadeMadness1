package com.mygdx.arcademadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Banzneri on 21/02/2017.
 */

/**
 *
 */
public abstract class Character {
    boolean upLeft, upRight, downRight, downLeft;

    ArcadeMadness host;

    Texture textureRight;
    Texture textureLeft;
    Texture textureUp;
    Texture textureDown;
    Rectangle rect;

    String direction;
    Boolean isInRoom = false;

    float speed = 1f;
    float speedX;
    float speedY;

    float collisionCooldownTimer = 0;
    float collisionCooldown = 0f;

    float widthInPixels = 15f;
    float heightInPixels = 15f;

    public Character(float x, float y, ArcadeMadness host) {
        this.host = host;

        rect = new Rectangle(x, y, widthInPixels, heightInPixels);
    }

    public Rectangle getRect() {
        return rect;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public boolean isInRoom() {
        return isInRoom;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void draw(SpriteBatch batch) {
        if(direction.equals("right")) {
            batch.draw(textureRight, rect.getX(), rect.getY(), widthInPixels, heightInPixels);
        } else if(direction.equals("left")) {
            batch.draw(textureLeft, rect.getX(), rect.getY(), widthInPixels, heightInPixels);
        } else if(direction.equals("up")){
            batch.draw(textureUp, rect.getX(), rect.getY(), widthInPixels, heightInPixels);
        } else {
            batch.draw(textureDown, rect.getX(), rect.getY(), widthInPixels, heightInPixels);
        }
    }

    public void move() {
        checkGameRoomCollision();
        float x = rect.getX() + speedX;
        float y = rect.getY() + speedY;

        getCorners(x, y);

        if (direction.equals("up") && (upRight && upLeft)) {
            rect.setPosition(x, y);
        } else if(direction.equals("right") && (upRight && downRight)) {
            rect.setPosition(x, y);
        } else if(direction.equals("left") && (upLeft && downLeft)) {
            rect.setPosition(x, y);
        } else if(direction.equals("down") && (downLeft & downRight)) {
            rect.setPosition(x, y);
        } else {
            turnRight();
        }
        checkCollision();

        if(rect.getX() > ArcadeMadness.worldWidth - ArcadeMadness.TILE_SIZE_IN_PIXELS) {
            rect.setX(ArcadeMadness.worldWidth - ArcadeMadness.TILE_SIZE_IN_PIXELS);
        }
        if(rect.getX() < ArcadeMadness.TILE_SIZE_IN_PIXELS) {
            rect.setX(ArcadeMadness.TILE_SIZE_IN_PIXELS);
        }
        if(rect.getY() > ArcadeMadness.worldHeight - ArcadeMadness.TILE_SIZE_IN_PIXELS) {
            rect.setY(ArcadeMadness.worldHeight - ArcadeMadness.TILE_SIZE_IN_PIXELS);
        }
        if(rect.getY() < ArcadeMadness.TILE_SIZE_IN_PIXELS) {
            rect.setY(ArcadeMadness.TILE_SIZE_IN_PIXELS);
        }
        checkArrows();
    }

    public void turnRight() {
        if(direction.equals("down")) {
            setDirectionLeft();
        } else if(direction.equals("up")) {
            setDirectionRight();
        } else if(direction.equals("left")) {
            setDirectionUp();
        } else {
            setDirectionDown();
        }

    }

    public void setDirectionLeft() {
        speedX = -speed;
        speedY = 0;
        direction = "left";
    }

    public void setDirectionRight() {
        speedX = speed;
        speedY = 0;
        direction = "right";
    }

    public void setDirectionUp() {
        speedX = 0;
        speedY = speed;
        direction = "up";
    }

    public void setDirectionDown() {
        speedX = 0;
        speedY = -speed;
        direction = "down";
    }

    public void setDirection(String direction) {
        this.direction = direction;

        if(direction.equals("up")) {
            setDirectionUp();
        }
        if(direction.equals("down")) {
            setDirectionDown();
        }
        if(direction.equals("right")) {
            setDirectionRight();
        }
        if(direction.equals("left")) {
            setDirectionLeft();
        }
    }

    public void getCorners(float x, float y) {
        float downY = y;
        float upY = downY + ArcadeMadness.TILE_SIZE_IN_PIXELS - speed;

        float leftX = x;
        float rightX = leftX + ArcadeMadness.TILE_SIZE_IN_PIXELS - speed;

        upLeft = host.isFree(leftX, upY);
        downLeft = host.isFree(leftX, downY);
        upRight = host.isFree(rightX, upY);
        downRight = host.isFree(rightX, downY);
    }

    public void checkArrows() {
        for(Arrow arrow : host.getArrowList()) {
            if(rect.getX() == arrow.getX() && rect.getY() == arrow.getY()) {
                setDirection(arrow.getDirection());
            }
        }
    }

    public void checkCollision() {
        collisionCooldownTimer += Gdx.graphics.getDeltaTime();
        for(Character character : host.getCharacterList()) {
            if(character != this && rect.overlaps(character.getRect()) && collisionCooldownTimer > collisionCooldown) {
                setDirection(getOpposite(direction));
                rect.setPosition(rect.getX() + speedX, rect.getY() + speedY);
                collisionCooldownTimer = 0;
            }
        }
    }

    public void checkGameRoomCollision() {
        MapLayer layer = host.getMap().getLayers().get("Rooms");
        MapObjects rooms = layer.getObjects();
        Array<RectangleMapObject> roomRectangleObjects = rooms.getByType(RectangleMapObject.class);
        for(RectangleMapObject object : roomRectangleObjects) {
            if(object.getRectangle().overlaps(getRect())) {
                isInRoom = true;
            }
        }
    }

    public String getOpposite(String direction) {
        if(direction.equals("down")) {
            return "up";
        } else if(direction.equals("up")) {
            return "down";
        } else if(direction.equals("right")) {
            return "left";
        } else {
            return "right";
        }
    }

    public String getDirection() {
        return direction;
    }
}