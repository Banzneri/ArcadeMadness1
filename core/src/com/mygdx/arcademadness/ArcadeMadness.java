package com.mygdx.arcademadness;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * The main game class
 */
public class ArcadeMadness extends Game {
    public static final float TILE_SIZE_IN_PIXELS = 32;
    public static final float TILES_AMOUNT_WIDTH = 25;
    public static final float TILES_AMOUNT_HEIGHT = 15;

    public static float worldWidth = TILE_SIZE_IN_PIXELS * TILES_AMOUNT_WIDTH;
    public static float worldHeight = TILE_SIZE_IN_PIXELS * TILES_AMOUNT_HEIGHT;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private MainMenuScreen mainMenuScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);
        camera.position.set(worldWidth / 2, worldHeight / 2, 0);

        mainMenuScreen =  new MainMenuScreen(this);
        setScreen(mainMenuScreen);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

}
