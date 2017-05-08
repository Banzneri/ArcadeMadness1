package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.arcademadness.ArcadeMadness;
import com.mygdx.arcademadness.Characters.SuperNerd;
import com.mygdx.arcademadness.GameObjects.Arrow;
import com.mygdx.arcademadness.Characters.Boy;
import com.mygdx.arcademadness.Characters.Character;
import com.mygdx.arcademadness.Characters.Child;
import com.mygdx.arcademadness.GameObjects.Entrance;
import com.mygdx.arcademadness.GameObjects.GameRoom;
import com.mygdx.arcademadness.Characters.Monster;
import com.mygdx.arcademadness.GifDecoder;
import com.mygdx.arcademadness.MyInputProcessor;
import com.mygdx.arcademadness.Characters.OldWoman;
import com.mygdx.arcademadness.Characters.Woman;
import java.util.ArrayList;

import sun.misc.IOUtils;

/**
 * Created by Banzneri on 16/03/2017.
 */

public abstract class GameScreen implements Screen {

    public boolean paused = false;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private TextureRegion menuArrow;
    private BitmapFont font;
    private BitmapFont fontAge;
    private Music music;
    private Texture zeroErrors;
    private Texture oneError;
    private Texture twoErrors;
    private Texture threeErrors;
    private Texture introText;

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

        Texture arrowSheet = new Texture("Arrows/arrows-gold-big.png");
        menuArrow = new TextureRegion(arrowSheet, 0, 32, 32, 32);
        zeroErrors = new Texture("0errors.png");
        oneError = new Texture("1errors.png");
        twoErrors = new Texture("2errors.png");
        threeErrors = new Texture("3errors.png");
        introText = new Texture(Gdx.files.internal("tutorial-text.png"));
        createFont();
        startMusic();

        helperArrow = new Arrow("up", 0, 0);

        initLists();
        initMap(mapName);
        addEntrances();
        addGameRooms();
        initInputProcessor();
    }

    @Override
    public void render (float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!paused) {
            host.getCamera().update();
            host.getBatch().setProjectionMatrix(host.getCamera().combined);

            renderer.render();
            renderer.setView(host.getCamera());

            host.getBatch().begin();
            Color c = host.getBatch().getColor();
            host.getBatch().setColor(c.r, c.g, c.b, 1f); // set alpha to 1

            drawAll();
            checkVictory();
            checkLose();
            removeCharacters();
            spawnCharacter();
            moveAll();
            removeGhosts();
            host.getBatch().end();
        }
    }

    public void initMap(String mapName) {
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapName);
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void pauseMusic() {
        music.pause();
    }

    public void continueMusic() {
        music.play();
    }

    public void initLists() {
        arrowList = new ArrayList<Arrow>();
        entranceList = new ArrayList<Entrance>();
        characterList = new ArrayList<Character>();
        gameRoomList = new ArrayList<GameRoom>();
    }

    public void initInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        GestureDetector gestureDetector = new GestureDetector(new MyInputProcessor(this));
        InputProcessor inputProcessor = new MyInputProcessor(this);
        multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(gestureDetector);

        Gdx.input.setInputProcessor(multiplexer);
    }

    public void createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        FreeTypeFontGenerator generatorAge = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ka1.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterAge = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterAge.size = 25;
        parameterAge.borderWidth = 2;
        fontAge = generatorAge.generateFont(parameterAge);
    }

    public void startMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/DayAndNight.wav"));
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();
    }

    public void drawMenu() {
        getFont().setColor(Color.WHITE);

        float startingXPosition = 4 * ArcadeMadness.TILE_SIZE_IN_PIXELS;

        for(int i = 1; i <= arrowAmount - arrowList.size(); i++) {
            if(i == 1) {
                host.getBatch().draw(menuArrow, startingXPosition, 5, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10);
            } else {
                host.getBatch().draw(menuArrow, startingXPosition - ArcadeMadness.TILE_SIZE_IN_PIXELS + i * ArcadeMadness.TILE_SIZE_IN_PIXELS, 5, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10, ArcadeMadness.TILE_SIZE_IN_PIXELS - 10);
            }
        }

        switch (mistakes) {
            case 0: host.getBatch().draw(zeroErrors, 11 * ArcadeMadness.TILE_SIZE_IN_PIXELS, ArcadeMadness.TILE_SIZE_IN_PIXELS - 32, 100, 32);
                    return;
            case 1: host.getBatch().draw(oneError, 11 * ArcadeMadness.TILE_SIZE_IN_PIXELS, ArcadeMadness.TILE_SIZE_IN_PIXELS - 32, 100, 32);
                    return;
            case 2: host.getBatch().draw(twoErrors, 11 * ArcadeMadness.TILE_SIZE_IN_PIXELS, ArcadeMadness.TILE_SIZE_IN_PIXELS - 32, 100, 32);
                    return;
            case 3: host.getBatch().draw(threeErrors, 11 * ArcadeMadness.TILE_SIZE_IN_PIXELS, ArcadeMadness.TILE_SIZE_IN_PIXELS - 32, 100, 32);
        }
    }

    @Override
    public void dispose () {
        music.dispose();
    }

    /**
     * Handles the movement of all the characters
     */
    public void moveAll() {
        for (Character character : characterList) {
            character.move();
        }
    }

    public void removeGhosts() {
        ArrayList<Character> toRemove = new ArrayList<Character>();

        for (Character character : characterList) {
            if(character instanceof Monster && ((Monster) character).hasExpired()) {
                toRemove.add(character);
            }
        }

        characterList.removeAll(toRemove);
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

    public BitmapFont getFontAge() {
        return fontAge;
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

    public void setPaused(boolean paused) {
        this.paused = paused;
        if(paused) {
           pauseMusic();
        } else {
            continueMusic();
        }
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

        if(isFree(x, y) && !isGameRoom(x, y)) {
            arrowList.add(new Arrow(direction, indexX * ArcadeMadness.TILE_SIZE_IN_PIXELS, indexY * ArcadeMadness.TILE_SIZE_IN_PIXELS));
        }
        if(arrowList.size() > arrowAmount) {
            arrowList.remove(0);
        }
    }

    /**
     * Iterates through the Rooms TiledMap layer, and creates GameRoom objects at the coordinates
     * received from the Rooms layer's Rectangle objects.
     */
    public void addGameRooms() {
        Array<RectangleMapObject> roomRectangleObjects = getObjectsFromLayer("Rooms");

        for(RectangleMapObject object : roomRectangleObjects) {
            gameRoomList.add(new GameRoom(object.getRectangle().getX(), object.getRectangle().getY(),
                            object.getRectangle().getWidth(), object.getRectangle().getHeight(), object.getName(), this));
        }

        Array<RectangleMapObject> numberObjects = getObjectsFromLayer("Numbers");

        for(RectangleMapObject object : numberObjects) {

            for(GameRoom gameRoom: gameRoomList) {
                if(Integer.parseInt(object.getName()) == gameRoom.getAgeRestriction()) {
                    Rectangle rectangle = new Rectangle(object.getRectangle().getX(), object.getRectangle().getY(), object.getRectangle().getWidth(), object.getRectangle().getHeight());
                    gameRoom.setNumberRectangle(rectangle);
                }
            }

        }
    }

    /**
     * Spawns characters using a timer. Uses the spawnRandomCharacter method
     */
    public void spawnCharacter() {
        characterSpawnTimer += Gdx.graphics.getDeltaTime();

        if(characterSpawnTimer > spawnInterval) {
            int rand = MathUtils.random(0, entranceList.size()-1);
            spawnRandomCharacter(entranceList.get(rand));
            characterSpawnTimer = 0;
        }
    }

    /**
     * Spawns a random character at the given entrance
     *
     * @param entrance
     */
    public void spawnRandomCharacter(Entrance entrance) {
        if(entrance.isFree()) {
            float x = entrance.getX();
            float y = entrance.getY();

            int rand = MathUtils.random(3);
            int monsterRand = MathUtils.random(10);
            int superNerdRand = MathUtils.random(10);

            if(monsterRand == 1) {
                Monster monster = new Monster(x, y, this, entrance.getDirection());
                characterList.add(monster);
            } else if(superNerdRand == 1) {
                SuperNerd superNerd = new SuperNerd(x, y, this, entrance.getDirection());
                characterList.add(superNerd);
            }

            else {

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

    /**
     * Removes characters from the game. Iterates through the characters and calls their isInRoom
     * method to check if the character should be removed from play. Also checks if the character
     * is in a wrong room, and if so, adds a mistake
     */
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
        Array<RectangleMapObject> roomRectangleObjects = getObjectsFromLayer("Entrances");

        for(RectangleMapObject object : roomRectangleObjects) {
            entranceList.add(new Entrance(object.getRectangle().getX(), object.getRectangle().getY(), this, object.getName()));
        }
    }

    /**
     * Draws all the arrows to the screen. Used in drawAll() method.
     */
    public void drawArrows() {
        for(Arrow arrow : arrowList) {
            arrow.draw(host.getBatch());
        }
    }

    /**
     * Draws the number of the people in the room. Iterates through all the game rooms, and calls
     * game room's drawNumberOfPeople method
     */
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
     * Checks if there is a game room in the given coordinates
     *
     * @param x the x coordinate of the room
     * @param y the y coordinate of the room
     * @return true if there is a game room in the given coordinates, false otherwise
     */
    public boolean isGameRoom(float x, float y) {
        for(GameRoom gameRoom: gameRoomList) {
            if(x >= gameRoom.getX() && x <= gameRoom.getX() + 3 * ArcadeMadness.TILE_SIZE_IN_PIXELS && y >= gameRoom.getY() && y <= gameRoom.getY() + 3 * ArcadeMadness.TILE_SIZE_IN_PIXELS) {
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

        drawMenu();
        drawStretchArrow();
        drawNumberOfPeople();
    }


    /**
     * Draws the stretching helper arrow. This should probably go to the Arrow class, but it's going
     * to stay here now
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
            if(isFree(arrowX, arrowY) && !isGameRoom(arrowX, arrowY)) {

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

    /**
     * Removes all arrows. Used when the player double taps the screen
     */
    public void removeAllArrows() {
        ArrayList<Arrow> toRemove = new ArrayList<Arrow>();

        for(Arrow arrow : arrowList) {
            toRemove.add(arrow);
        }

        arrowList.removeAll(toRemove);
    }

    /**
     * Removes an arrow in the given coordinates
     * @param x the x coordinate of the arrow
     * @param y the y coordinate of the arrow
     */
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

    /**
     * Iterates through the gamerooms and checks if the victory condition is met
     *
     * @return true if victory condition is met, false otherwise
     */
    public boolean isWin() {
        for(GameRoom gameRoom : gameRoomList) {
            if(gameRoom.getNumberOfPeople() < 4) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the player has won, if so, sets the winning game end screen. Uses isWin() method for
     * checking victory condition
     */
    public void checkVictory() {
        if(isWin()) {
            Gdx.app.log("", "You win!");
            host.setNextLevel(host.getNextLevel() + 1);
            dispose();
            getHost().setScreen(new GameEndScreen(getHost(), true));
        }
    }

    /**
     * Checks if the player has lost, if so, sets the losing game end screen
     */
    public void checkLose() {
        if(mistakes >= 3) {
            Gdx.app.log("", "You lose!");
            dispose();
            getHost().setScreen(new GameEndScreen(getHost(), false));
        }
    }

    /**
     * Returns Rectangle map objects from a layer with the given layerName string
     * @param layerName
     * @return
     */
    public Array<RectangleMapObject> getObjectsFromLayer(String layerName) {
        MapLayer layer = getMap().getLayers().get(layerName);
        MapObjects objects = layer.getObjects();

        return objects.getByType(RectangleMapObject.class);
    }

    /**
     * Draws the age of the characters on top of their heads in the given coordinates
     * @param x the x position of the age text
     * @param y the y position of the age text
     */
    public void drawAge(float x, float y) {
        Vector3 vector3 = new Vector3(x, y, 0);
        host.getCamera().unproject(vector3);

        float mx = vector3.x;
        float my = vector3.y;

        for(Character character: characterList) {
            if(mx > character.getRect().getX() && mx < character.getRect().getX() + character.getRect().getWidth() &&
                    my > character.getRect().getY() && my < character.getRect().getY() + character.getRect().getWidth()) {
                character.startAgeDraw();
            }
        }

        Gdx.app.log("", mx + " " + my);
    }
}
