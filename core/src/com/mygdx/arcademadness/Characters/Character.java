package com.mygdx.arcademadness.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.arcademadness.ArcadeMadness;
import com.mygdx.arcademadness.GameObjects.Arrow;
import com.mygdx.arcademadness.GameObjects.GameRoom;

/**
 * Created by Banzneri on 21/02/2017.
 */

/**
 *
 */
public abstract class Character {
    boolean upLeft, upRight, downRight, downLeft;

    com.mygdx.arcademadness.Screens.GameScreen host;

    Texture textureRight;
    Texture textureLeft;
    Texture textureUp;
    Texture textureDown;
    Rectangle rect;
    Sound rightRoomSound;
    Sound wrongRoomSound;
    Sound monsterEnterRoomSound;

    String direction;
    int age;
    Boolean isInRoom = false;
    Boolean wrongRoom = false;

    float speed = 1f;
    float speedX;
    float speedY;
    long prevTime;

    float collisionCooldownTimer = 0;
    float collisionCooldown = 0f;

    float widthInPixels = 31f;
    float heightInPixels = 31f;

    public Character(float x, float y, com.mygdx.arcademadness.Screens.GameScreen host) {
        this.host = host;

        rect = new Rectangle(x, y, widthInPixels, heightInPixels);

        rightRoomSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sharp_echo.wav"));
        wrongRoomSound = Gdx.audio.newSound(Gdx.files.internal("sounds/error.wav"));
        monsterEnterRoomSound = Gdx.audio.newSound(Gdx.files.internal("sounds/scream.wav"));
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

    public boolean isInWrongRoom() {
        return wrongRoom;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

        drawAge();
    }

    public void startAgeDraw() {
        prevTime = System.currentTimeMillis();
    }

    public void drawAge() {
        if(TimeUtils.timeSinceMillis(prevTime) < 2500) {
            if(age<16) {
                host.getFontAge().setColor(Color.GREEN);
            } else if(age<18) {
                host.getFontAge().setColor(Color.ORANGE);
            } else {
                host.getFontAge().setColor(Color.RED);
            }

            if(age < 9) {
                host.getFontAge().draw(host.getHost().getBatch(), Integer.toString(age), getRect().getX(), getRect().getY() + getRect().getHeight() * 2.3f);
            } else if(age > 100) {
                host.getFontAge().draw(host.getHost().getBatch(), Integer.toString(age), getRect().getX() - 20, getRect().getY() + getRect().getHeight() * 2.3f);
            }
            else {
                host.getFontAge().draw(host.getHost().getBatch(), Integer.toString(age), getRect().getX() - 10, getRect().getY() + getRect().getHeight() * 2.3f);
            }
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
            makeATurn();
        }
        checkCharacterCollision();

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

    public void turnLeft() {
        if(direction.equals("down")) {
            setDirectionRight();
        } else if(direction.equals("up")) {
            setDirectionLeft();
        } else if(direction.equals("left")) {
            setDirectionDown();
        } else {
            setDirectionUp();
        }
    }

    public void makeATurn() {
        int rand = MathUtils.random(0, 1);

        if(rand == 0) {
            turnRight();
        } else {
            turnLeft();
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

    public void checkCharacterCollision() {
        collisionCooldownTimer += Gdx.graphics.getDeltaTime();
        for(Character character : host.getCharacterList()) {
            if(character != this && rect.overlaps(character.getRect()) && collisionCooldownTimer > collisionCooldown) {
                setDirection(getOppositeDirection(direction));
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

                for(GameRoom gameRoom : host.getGameRoomList()) {
                    if(gameRoom.getX() == object.getRectangle().getX() && gameRoom.getY() == object.getRectangle().getY()) {

                        if(this instanceof Monster) {
                            gameRoom.setNumberOfPeopleToZero();
                            monsterEnterRoomSound.play(0.3f);
                        } else if(this instanceof SuperNerd) {
                            gameRoom.fillRoom();
                        } else if(age < gameRoom.getAgeRestriction()) {
                            wrongRoom = true;
                            wrongRoomSound.play(2);
                            gameRoom.addCharacter();
                        } else {
                            rightRoomSound.play(2);
                            gameRoom.addCharacter();
                        }
                    }
                }
            }
        }
    }

    public String getOppositeDirection(String direction) {
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
