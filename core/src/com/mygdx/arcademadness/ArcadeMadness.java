package com.mygdx.arcademadness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.arcademadness.Screens.IntroScreen;

/**
 * The main game class
 */
public class ArcadeMadness extends Game {
    public static final float TILE_SIZE_IN_PIXELS = 32;
    public static final float TILES_AMOUNT_WIDTH = 21;
    public static final float TILES_AMOUNT_HEIGHT = 13;

    public static float worldWidth = TILE_SIZE_IN_PIXELS * TILES_AMOUNT_WIDTH;
    public static float worldHeight = TILE_SIZE_IN_PIXELS * TILES_AMOUNT_HEIGHT;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private com.mygdx.arcademadness.Screens.IntroScreen introScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);
        camera.position.set(worldWidth / 2, worldHeight / 2, 0);

        introScreen =  new com.mygdx.arcademadness.Screens.IntroScreen(this);
        setScreen(introScreen);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

}
