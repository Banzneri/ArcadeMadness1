package com.mygdx.arcademadness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.arcademadness.Screens.GameScreen;
import com.mygdx.arcademadness.Screens.Level1;
import com.mygdx.arcademadness.Screens.Level2;
import com.mygdx.arcademadness.Screens.Level3;
import com.mygdx.arcademadness.Screens.Level4;

/**
 * The main game class
 */
public class ArcadeMadness extends Game {
    public static final float TILE_SIZE_IN_PIXELS = 32;
    public static final float TILES_AMOUNT_WIDTH = 18;
    public static final float TILES_AMOUNT_HEIGHT = 11;

    public boolean firstTime = true;

    public static float worldWidth = TILE_SIZE_IN_PIXELS * TILES_AMOUNT_WIDTH;
    public static float worldHeight = TILE_SIZE_IN_PIXELS * TILES_AMOUNT_HEIGHT;

    public int nextLevel;
    public BitmapFont font;
    public BitmapFont redFont;
    public BitmapFont greenFont;
    public Animation<TextureRegion> introAnim1;
    public Animation<TextureRegion> introAnim2;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private com.mygdx.arcademadness.Screens.IntroScreen introScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);
        camera.position.set(worldWidth / 2, worldHeight / 2, 0);

        nextLevel = 1;
        createIntroAnimations();
        createFont();

        introScreen =  new com.mygdx.arcademadness.Screens.IntroScreen(this);
        setScreen(introScreen);
    }

    public Animation<TextureRegion> getIntroAnim1() {
        return introAnim1;
    }

    public Animation<TextureRegion> getIntroAnim2() {
        return introAnim2;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;

        if(this.nextLevel > 4) {
            this.nextLevel = 4;
        }

        if(this.nextLevel < 1) {
            this.nextLevel = 1;
        }
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void createIntroAnimations() {
        introAnim1 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tutorial1-en.gif").read());
        introAnim2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tutorial2-en.gif").read());
    }

    public void createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ka1.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        parameter.color = Color.RED;
        redFont = generator.generateFont(parameter);

        parameter.color = Color.GREEN;
        greenFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public BitmapFont getFont() {
        return font;
    }

    public GameScreen getNextLevelObject() {

        switch (nextLevel) {
            case 1: return new Level1(this);
            case 2: return new Level2(this);
            case 3: return new Level3(this);
        }

        return new Level4(this);
    }

}
