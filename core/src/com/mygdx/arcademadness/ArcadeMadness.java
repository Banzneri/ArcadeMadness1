package com.mygdx.arcademadness;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * The main game class
 */
public class ArcadeMadness extends ApplicationAdapter implements GestureDetector.GestureListener{
    public static final float TILE_SIZE_IN_PIXELS = 16;
    public static final float TILES_AMOUNT_WIDTH = 25;
    public static final float TILES_AMOUNT_HEIGHT = 15;

    public static float worldWidth = TILE_SIZE_IN_PIXELS * TILES_AMOUNT_WIDTH;
    public static float worldHeight= TILE_SIZE_IN_PIXELS * TILES_AMOUNT_HEIGHT;

    private SpriteBatch batch;
	private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TmxMapLoader mapLoader;
    private Texture logoTexture;

    private float arrowX;
    private float arrowY;
    private float arrowAmount = 4;
    private Arrow helperArrow;

    private ArrayList<Arrow> arrowList;
    private ArrayList<Entrance> entranceList;
    private ArrayList<Character> characterList;

	@Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);
        camera.position.set(worldWidth / 2, worldHeight / 2, 0);

        logoTexture = new Texture("am.png");

        helperArrow = new Arrow("up", 0, 0);

        arrowList = new ArrayList<Arrow>();
        entranceList = new ArrayList<Entrance>();

        addCharacters();
        addEntrances();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("am-landscape2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        Gdx.input.setInputProcessor(new GestureDetector(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.render();
        renderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);

        moveAll();

		batch.begin();
        drawAll();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

    /**
     * Handles the movement of all the characters
     */
    public void moveAll() {
        for (Character character : characterList) {
            character.move();
        }
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

    /**
     * Checks if the tile at the given coordinates is free to enter; not a wall
     *
     * @param x the x coordinate of the tile
     * @param y the y coordinate of the tile
     * @return
     */
    public boolean isFree(float x, float y) {
        int indexX = (int) (x / TILE_SIZE_IN_PIXELS);
        int indexY = (int) (y / TILE_SIZE_IN_PIXELS);

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
        int indexX = (int) (x / TILE_SIZE_IN_PIXELS);
        int indexY = (int) (y / TILE_SIZE_IN_PIXELS);

        if(isFree(x, y)) {
            arrowList.add(new Arrow(direction, indexX * 16, indexY * 16));
        }
        if(arrowList.size() > arrowAmount) {
            arrowList.remove(0);
        }
    }

    /**
     * Adds all the characters to the Character list
     */
    public void addCharacters() {
        characterList = new ArrayList<Character>();
        characterList.add(new Monster(16, worldHeight / 2 - 8, this, "right"));
        characterList.add(new Woman(worldWidth / 2 - 8, 16, this, "up"));
        characterList.add(new Woman(worldWidth / 2 - 8, worldHeight - 16, this, "down"));
        characterList.add(new Woman(worldWidth - 16, worldHeight / 2 - 8, this, "left"));
    }

    /**
     * Adds the entrance objects to the Entrance list
     */
    public void addEntrances() {
        entranceList.add(new Entrance(worldWidth / 2 - 8, worldHeight - 16));
    }

    /**
     * Draws all the arrows to the screen. Used in drawAll() method.
     */
    public void drawArrows() {
        for(Arrow arrow : arrowList) {
            arrow.draw(batch);
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
        int indexX = (int) (arrowX / TILE_SIZE_IN_PIXELS);
        int indexY = (int) (arrowY / TILE_SIZE_IN_PIXELS);

        for(Arrow arrow : arrowList) {
            if(arrow.getX() == indexX * TILE_SIZE_IN_PIXELS && arrow.getY() == indexY * TILE_SIZE_IN_PIXELS) {
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
        drawStretchArrow();
        drawArrows();

        for(Character character : characterList) {
            character.draw(batch);
        }

        batch.draw(logoTexture, 0, worldHeight + 20, worldWidth, logoTexture.getHeight());
    }


    /**
     * Draws the stretching helper arrow
     */
    public void drawStretchArrow() {
        if(Gdx.input.isTouched()) {
            int realY = Gdx.input.getY();
            int realX = Gdx.input.getX();

            Vector3 touchPos = new Vector3(realX, realY, 0);

            camera.unproject(touchPos);

            int indexX = (int) (arrowX / TILE_SIZE_IN_PIXELS);
            int indexY = (int) (arrowY / TILE_SIZE_IN_PIXELS);

            float x = indexX * TILE_SIZE_IN_PIXELS;
            float y = indexY * TILE_SIZE_IN_PIXELS;

            helperArrow.setX(x);
            helperArrow.setY(y);
            helperArrow.setWidth(TILE_SIZE_IN_PIXELS);
            helperArrow.setHeight(TILE_SIZE_IN_PIXELS);

            // Draws the helper arrow only if the tile is free
            if(isFree(arrowX, arrowY)) {

                // If the arrow is pulled to the right
                if (Math.abs(touchPos.x - arrowX) > Math.abs(touchPos.y - arrowY) && touchPos.x > arrowX) {
                    helperArrow.setDirection("right");
                    helperArrow.setWidth(touchPos.x - arrowX);
                }
                // If the arrow is pulled to the left
                else if (Math.abs(touchPos.x - arrowX) > Math.abs(touchPos.y - arrowY) && touchPos.x < arrowX) {
                    helperArrow.setX(x + TILE_SIZE_IN_PIXELS);
                    helperArrow.setDirection("right");
                    helperArrow.setWidth(touchPos.x - arrowX);
                }
                // If the arrow is pulled down
                else if (touchPos.y < arrowY) {
                    helperArrow.setY(y + TILE_SIZE_IN_PIXELS);
                    helperArrow.setDirection("up");
                    helperArrow.setHeight(touchPos.y - arrowY);
                }
                // If the arrow is pulled up
                else {
                    helperArrow.setDirection("up");
                    helperArrow.setHeight(touchPos.y - arrowY);
                }

                helperArrow.draw(batch);
                Gdx.app.log("X", Float.toString(x));
                Gdx.app.log("Y", Float.toString(y));
            }
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        // Sets the arrow coordinates when the users finger touches down on the screen.
        // The arrowX and arrowY parameters are used in the fling() method, where the actual placement
        // of the arrow happens.
        int realY = Gdx.input.getY();
        int realX = Gdx.input.getX();

        Vector3 touchPos = new Vector3(realX, realY, 0);

        camera.unproject(touchPos);

        arrowX = touchPos.x;
        arrowY = touchPos.y;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        if(Math.abs(velocityX) > Math.abs(velocityY) && velocityX > 0 && !isArrow("right")) {
            addArrow("right", arrowX, arrowY);
        } else if(Math.abs(velocityX) > Math.abs(velocityY) && velocityX < 0 && !isArrow("left")) {
            addArrow("left", arrowX, arrowY);
        } else if(Math.abs(velocityX) < Math.abs(velocityY) && velocityY > 0 && !isArrow("down")) {
            addArrow("down", arrowX, arrowY);
        } else if(Math.abs(velocityX) < Math.abs(velocityY) && velocityY < 0 && !isArrow("up")) {
            addArrow("up", arrowX, arrowY);
        }

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
