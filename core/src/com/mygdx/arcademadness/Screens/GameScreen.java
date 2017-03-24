package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.arcademadness.ArcadeMadness;
import com.mygdx.arcademadness.GameObjects.Arrow;
import com.mygdx.arcademadness.Characters.Boy;
import com.mygdx.arcademadness.Characters.Character;
import com.mygdx.arcademadness.Characters.Child;
import com.mygdx.arcademadness.GameObjects.Entrance;
import com.mygdx.arcademadness.GameObjects.GameRoom;
import com.mygdx.arcademadness.Characters.Monster;
import com.mygdx.arcademadness.MyInputProcessor;
import com.mygdx.arcademadness.Characters.OldWoman;
import com.mygdx.arcademadness.Characters.Woman;

import java.util.ArrayList;

/**
 * Created by Banzneri on 16/03/2017.
 */

public abstract class GameScreen implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TmxMapLoader mapLoader;

    private Texture logoTexture;
    private Sprite lightTexture;
    private TextureRegion menuArrow;
    private BitmapFont font;

    private float arrowX;
    private float arrowY;
    private float touchDownX;
    private float touchDownY;
    private float arrowAmount = 4;
    private Arrow helperArrow;

    private float characterSpawnTimer = 0;
    private float spawnInterval;
    private int mistakes = 0;

    private ArrayList<Arrow> arrowList;
    private ArrayList<Entrance> entranceList;
    private ArrayList<Character> characterList;
    private ArrayList<GameRoom> gameRoomList;

    private ArcadeMadness host;

    public GameScreen(String mapName, float spawnInterval, ArcadeMadness host) {
        this.spawnInterval = spawnInterval;
        this.host = host;

        logoTexture = new Texture("am.png");
        lightTexture = new Sprite(new Texture("light.png"));
        Texture arrowSheet = new Texture("arrows-gold-big.png");
        menuArrow = new TextureRegion(arrowSheet, 0, 32, 32, 32);
        createFont();

        helperArrow = new Arrow("up", 0, 0);

        arrowList = new ArrayList<Arrow>();
        entranceList = new ArrayList<Entrance>();
        characterList = new ArrayList<Character>();
        gameRoomList = new ArrayList<GameRoom>();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapName);
        renderer = new OrthogonalTiledMapRenderer(map);

        addEntrances();
        addGameRooms();

        InputMultiplexer multiplexer = new InputMultiplexer();
        GestureDetector gestureDetector = new GestureDetector(new MyInputProcessor(this));
        InputProcessor inputProcessor = new MyInputProcessor(this);
        multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(gestureDetector);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render (float dt) {
        checkVictory();
        checkLose();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        host.getCamera().update();
        renderer.render();
        renderer.setView(host.getCamera());
        host.getBatch().setProjectionMatrix(host.getCamera().combined);

        removeCharacters();
        spawnCharacter();
        moveAll();

        host.getBatch().begin();
        Color c = host.getBatch().getColor();
        host.getBatch().setColor(c.r, c.g, c.b, 1f); // set alpha to 1
        drawAll();
        host.getBatch().end();
        //Gdx.app.log("fps", Float.toString(Gdx.graphics.getFramesPerSecond()));
    }

    public void createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
    }

    public void drawMenu() {
        float startingXPosition = 4 * ArcadeMadness.TILE_SIZE_IN_PIXELS;

        for(int i = 1; i <= arrowAmount - arrowList.size(); i++) {
            if(i == 1) {
                host.getBatch().draw(menuArrow, startingXPosition, 5, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10);
            } else {
                host.getBatch().draw(menuArrow, startingXPosition - ArcadeMadness.TILE_SIZE_IN_PIXELS + i * ArcadeMadness.TILE_SIZE_IN_PIXELS, 5, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10);
            }
        }

        getFont().draw(host.getBatch(), "Virheet: " + Integer.toString(mistakes), 12 * ArcadeMadness.TILE_SIZE_IN_PIXELS, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10);
    }

    public void drawLight() {
        host.getBatch().setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA);
        lightTexture.setPosition(0, ArcadeMadness.worldHeight - 32);
        lightTexture.setSize(32, 32);
        lightTexture.draw(host.getBatch(), 1f);
        host.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void dispose () {
        host.getBatch().dispose();
    }

    /**
     * Handles the movement of all the characters
     */
    public void moveAll() {
        for (Character character : characterList) {
            character.move();
        }
    }

    public ArcadeMadness getHost() {
        return host;
    }

    /**
     * The getter for the Tiled map object
     *
     * @return the map object
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * The getter for the character list
     *
     * @return the Character array list
     */
    public ArrayList<Character> getCharacterList() {
        return characterList;
    }

    /**
     * The getter for the Arrow list
     *
     * @return the Arrow array list
     */
    public ArrayList<Arrow> getArrowList() {
        return arrowList;
    }

    public ArrayList<GameRoom> getGameRoomList() {
        return gameRoomList;
    }

    public float getArrowX() {
        return arrowX;
    }

    public float getArrowY() {
        return arrowY;
    }

    public float getTouchDownX() {
        return touchDownX;
    }

    public float getTouchDownY() {
        return touchDownY;
    }

    public BitmapFont getFont() {
        return font;
    }

    public float getMistakes() {
        return mistakes;
    }

    public void setArrowX(float x) {
        arrowX = x;
    }

    public void setArrowY(float y) {
        arrowY = y;
    }

    public void setTouchDownX(float x) {
        touchDownX = x;
    }

    public void setTouchDownY(float y) {
        touchDownY = y;
    }

    public void addMistake() {
        mistakes++;
    }

    /**
     * Checks if the tile at the given coordinates is free to enter
     *
     * @param x the x coordinate of the tile
     * @param y the y coordinate of the tile
     * @return
     */
    public boolean isFree(float x, float y) {
        int indexX = (int) (x / ArcadeMadness.TILE_SIZE_IN_PIXELS);
        int indexY = (int) (y / ArcadeMadness.TILE_SIZE_IN_PIXELS);

        TiledMapTileLayer walls = (TiledMapTileLayer) getMap().getLayers().get("Graphics");

        if(walls.getCell(indexX, indexY) != null) {
            return false;
        }

        return true;
    }

    /**
     * Adds an arrow to the Arrow list, and removes the oldest arrow if arrows are over the limit
     *
     * @param direction the direction of the arrow
     * @param x the x coordinate of the arrow
     * @param y the y coordinate of the arrow
     */
    public void addArrow(String direction, float x, float y) {
        int indexX = (int) (x / ArcadeMadness.TILE_SIZE_IN_PIXELS);
        int indexY = (int) (y / ArcadeMadness.TILE_SIZE_IN_PIXELS);

        if(isFree(x, y)) {
            arrowList.add(new Arrow(direction, indexX * ArcadeMadness.TILE_SIZE_IN_PIXELS, indexY * ArcadeMadness.TILE_SIZE_IN_PIXELS));
        }
        if(arrowList.size() > arrowAmount) {
            arrowList.remove(0);
        }
    }

    public void addGameRooms() {
        MapLayer layer = getMap().getLayers().get("Rooms");
        MapObjects rooms = layer.getObjects();
        Array<RectangleMapObject> roomRectangleObjects = rooms.getByType(RectangleMapObject.class);

        for(RectangleMapObject object : roomRectangleObjects) {
            gameRoomList.add(new GameRoom(object.getRectangle().getX(), object.getRectangle().getY(),
                            object.getRectangle().getWidth(), object.getRectangle().getHeight(), object.getName(), this));
        }
    }

    public void spawnCharacter() {
        characterSpawnTimer += Gdx.graphics.getDeltaTime();

        if(characterSpawnTimer > spawnInterval) {
            int rand = MathUtils.random(0, entranceList.size()-1);
            spawnRandomCharacter(entranceList.get(rand));
            characterSpawnTimer = 0;
        }
    }

    public void spawnRandomCharacter(Entrance entrance) {
        if(entrance.isFree()) {
            float x = entrance.getX();
            float y = entrance.getY();

            int rand = MathUtils.random(3);
            int monsterRand = MathUtils.random(9);

            if(monsterRand == 1) {
                Monster monster = new Monster(x, y, this, entrance.getDirection());
                characterList.add(monster);
            } else {

                if (rand == 0) {
                    Woman woman = new Woman(x, y, this, entrance.getDirection());
                    characterList.add(woman);
                } else if (rand == 1) {
                    Boy boy = new Boy(x, y, this, entrance.getDirection());
                    characterList.add(boy);
                } else if (rand == 2) {
                    OldWoman oldWoman = new OldWoman(x, y, this, entrance.getDirection());
                    characterList.add(oldWoman);
                } else if (rand == 3) {
                    Child child = new Child(x, y, this, entrance.getDirection());
                    characterList.add(child);
                }
            }
        }
    }

    public void removeCharacters() {
        ArrayList<Character> removeCharacter = new ArrayList<Character>();

        for(Character character : characterList) {
            if(character.isInRoom()) {
                if(character.isInWrongRoom()) {
                    addMistake();
                }
                removeCharacter.add(character);
            }
        }

        characterList.removeAll(removeCharacter);
    }

    /**
     * Adds the entrance objects to the Entrance list
     */
    public void addEntrances() {
        entranceList.add(new Entrance(ArcadeMadness.TILE_SIZE_IN_PIXELS, ArcadeMadness.worldHeight / 2 - ArcadeMadness.TILE_SIZE_IN_PIXELS / 2, this, "right"));
        entranceList.add(new Entrance(ArcadeMadness.worldWidth / 2 - ArcadeMadness.TILE_SIZE_IN_PIXELS / 2, ArcadeMadness.TILE_SIZE_IN_PIXELS, this, "up"));
        entranceList.add(new Entrance(ArcadeMadness.worldWidth / 2 - ArcadeMadness.TILE_SIZE_IN_PIXELS / 2, ArcadeMadness.worldHeight - ArcadeMadness.TILE_SIZE_IN_PIXELS, this, "down"));
        entranceList.add(new Entrance(ArcadeMadness.worldWidth - ArcadeMadness.TILE_SIZE_IN_PIXELS, ArcadeMadness.worldHeight / 2 - ArcadeMadness.TILE_SIZE_IN_PIXELS / 2, this, "left"));
    }

    /**
     * Draws all the arrows to the screen. Used in drawAll() method.
     */
    public void drawArrows() {
        for(Arrow arrow : arrowList) {
            arrow.draw(host.getBatch());
        }
    }

    public void drawNumberOfPeople() {
        for(GameRoom gameRoom : gameRoomList) {
            gameRoom.drawNumberOfPeople();
        }
    }

    /**
     * Checks if a tile contains an arrow, and if it does, sets its direction to the given parameter.
     * Used in the gesturelistener fling() method, to check for an arrow and set it in the same method
     *
     * @param direction the direction for the arrow
     * @return
     */
    public boolean isArrow(String direction) {
        int indexX = (int) (arrowX / ArcadeMadness.TILE_SIZE_IN_PIXELS);
        int indexY = (int) (arrowY / ArcadeMadness.TILE_SIZE_IN_PIXELS);

        for(Arrow arrow : arrowList) {
            if(arrow.getX() == indexX * ArcadeMadness.TILE_SIZE_IN_PIXELS && arrow.getY() == indexY * ArcadeMadness.TILE_SIZE_IN_PIXELS) {
                arrow.setDirection(direction);
                return true;
            }
        }

        return false;
    }

    /**
     * Handles all the drawing (except for the Tiled map)
     */
    public void drawAll() {
        drawArrows();

        for(Character character : characterList) {
            character.draw(host.getBatch());
        }

        host.getBatch().draw(logoTexture, 0, ArcadeMadness.worldHeight + 20, ArcadeMadness.worldWidth, logoTexture.getHeight());
        drawMenu();
        drawLight();

        drawStretchArrow();
        drawNumberOfPeople();
    }


    /**
     * Draws the stretching helper arrow. This should probably go to the Arrow class.
     */
    public void drawStretchArrow() {
        // Makes the arrow transparent
        Color c = host.getBatch().getColor();
        host.getBatch().setColor(c.r, c.g, c.b, 0.5f);

        if(Gdx.input.isTouched()) {
            int realY = Gdx.input.getY();
            int realX = Gdx.input.getX();

            Vector3 touchPos = new Vector3(realX, realY, 0);

            host.getCamera().unproject(touchPos);

            int indexX = (int) (arrowX / ArcadeMadness.TILE_SIZE_IN_PIXELS);
            int indexY = (int) (arrowY / ArcadeMadness.TILE_SIZE_IN_PIXELS);

            float x = indexX * ArcadeMadness.TILE_SIZE_IN_PIXELS;
            float y = indexY * ArcadeMadness.TILE_SIZE_IN_PIXELS;

            helperArrow.setX(x);
            helperArrow.setY(y);
            helperArrow.setWidth(ArcadeMadness.TILE_SIZE_IN_PIXELS);
            helperArrow.setHeight(ArcadeMadness.TILE_SIZE_IN_PIXELS);

            // Draws the helper arrow only if the tile is free
            if(isFree(arrowX, arrowY)) {

                // If the arrow is pulled to the right
                if (Math.abs(touchPos.x - arrowX) > Math.abs(touchPos.y - arrowY) && touchPos.x > arrowX) {
                    helperArrow.setDirection("right");
                    helperArrow.setWidth(touchPos.x - arrowX);
                }
                // If the arrow is pulled to the left
                else if (Math.abs(touchPos.x - arrowX) > Math.abs(touchPos.y - arrowY) && touchPos.x < arrowX) {
                    helperArrow.setX(x + ArcadeMadness.TILE_SIZE_IN_PIXELS);
                    helperArrow.setDirection("right");
                    helperArrow.setWidth(touchPos.x - arrowX);
                }
                // If the arrow is pulled down
                else if (touchPos.y < arrowY) {
                    helperArrow.setY(y + ArcadeMadness.TILE_SIZE_IN_PIXELS);
                    helperArrow.setDirection("up");
                    helperArrow.setHeight(touchPos.y - arrowY);
                }
                // If the arrow is pulled up
                else {
                    helperArrow.setDirection("up");
                    helperArrow.setHeight(touchPos.y - arrowY);
                }

                helperArrow.draw(host.getBatch());
            }
        }
    }

    public void removeAllArrows() {
        ArrayList<Arrow> toRemove = new ArrayList<Arrow>();

        for(Arrow arrow : arrowList) {
            toRemove.add(arrow);
        }

        arrowList.removeAll(toRemove);
    }

    public void removeArrow(float x, float y) {
        Vector3 touchPos = new Vector3(x, y, 0);

        host.getCamera().unproject(touchPos);

        int indexX = (int) (arrowX / ArcadeMadness.TILE_SIZE_IN_PIXELS);
        int indexY = (int) (arrowY / ArcadeMadness.TILE_SIZE_IN_PIXELS);

        float mx = indexX * ArcadeMadness.TILE_SIZE_IN_PIXELS;
        float my = indexY * ArcadeMadness.TILE_SIZE_IN_PIXELS;

        for (Arrow arrow : arrowList) {
            if (mx == arrow.getX() && my == arrow.getY()) {
                arrowList.remove(arrow);
                break;
            }
        }
    }

    public boolean isWin() {
        for(GameRoom gameRoom : gameRoomList) {
            if(gameRoom.getNumberOfPeople() < 4) {
                return false;
            }
        }

        return true;
    }

    public void checkVictory() {
        if(isWin()) {
            Gdx.app.log("", "You win!");
            getHost().setScreen(new GameEndScreen(getHost()));
        }
    }

    public void checkLose() {
        if(mistakes >= 3) {
            Gdx.app.log("", "You lose!");
            getHost().setScreen(new GameEndScreen(getHost()));
        }
    }
}
