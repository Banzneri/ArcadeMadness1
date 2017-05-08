package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.arcademadness.*;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class MainMenuScreen implements Screen {
    TmxMapLoader mapLoader;
    TiledMap map;
    TiledMapRenderer renderer;
    ArcadeMadness host;
    Texture chosenLevel;
    Texture lock;
    int currentLevel = 1;
    ImageButton playButton;
    ImageButton settingsButton;
    ImageButton leftJoystick;
    ImageButton rightJoystick;
    Stage stage;
    float screenX;
    float screenY;
    float screenWidth;
    float screenHeight;
    Sound menuSound;
    Sound playSound;
    int unlockedLevels;

    public MainMenuScreen(ArcadeMadness host) {
        this.host = host;
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("main-menu-wider.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        chosenLevel = new Texture(Gdx.files.internal("LevelPreviews/level1-preview.png"));
        lock = new Texture(Gdx.files.internal("locked.png"));

        menuSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click_2.wav"));
        playSound = Gdx.audio.newSound(Gdx.files.internal("sounds/misc_menu_4.wav"));

        setArcadeScreen();
        setButtons();
        addListeners();
        initStage();
        initPreferences();
        checkUnlockedLevels();

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

    }

    public void checkUnlockedLevels() {
        Preferences preferences = Gdx.app.getPreferences("ArcadeMadnessPrefs");
        unlockedLevels = preferences.getInteger("unlockedLevel");
        Gdx.app.log("", Integer.toString(unlockedLevels));
        preferences.flush();
    }

    public void initPreferences() {
        Preferences preferences = Gdx.app.getPreferences("ArcadeMadnessPrefs");

        if(!preferences.contains("unlockedLevel")) {
            preferences.putInteger("unlockedLevel", 1);
            preferences.putBoolean("soundOn", true);
            preferences.putBoolean("tutorials", true);
        }

        preferences.flush();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0f, 0.3f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        host.getCamera().update();
        renderer.render();
        renderer.setView(host.getCamera());
        host.getBatch().setProjectionMatrix(host.getCamera().combined);

        stage.act();
        stage.draw();

        host.getBatch().begin();
        drawChosenLevel();
        if(currentLevel > unlockedLevels) {
            drawLock();
        }
        host.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        stage.dispose();
        this.dispose();
    }

    public void setButtons() {
        MapLayer layer = map.getLayers().get("Buttons");
        MapObjects buttons = layer.getObjects();
        Array<RectangleMapObject> roomRectangleObjects = buttons.getByType(RectangleMapObject.class);

        for(RectangleMapObject object : roomRectangleObjects) {
            if(object.getName().equals("play-button")) {
                playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/play-button.png"))));
                playButton.setSize(object.getRectangle().getWidth(), object.getRectangle().getHeight());
                playButton.setPosition(object.getRectangle().getX(), object.getRectangle().getY());
            } else if(object.getName().equals("settings-button")) {
                settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/settings-button.png"))));
                settingsButton.setSize(object.getRectangle().getWidth(), object.getRectangle().getHeight());
                settingsButton.setPosition(object.getRectangle().getX(), object.getRectangle().getY());
            } else if(object.getName().equals("left-stick")) {
                leftJoystick = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/left-arrow.png"))));
                leftJoystick.setSize(object.getRectangle().getWidth(), object.getRectangle().getHeight());
                leftJoystick.setPosition(object.getRectangle().getX(), object.getRectangle().getY());
            } else if(object.getName().equals("right-stick")) {
                rightJoystick = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/right-arrow.png"))));
                rightJoystick.setSize(object.getRectangle().getWidth(), object.getRectangle().getHeight());
                rightJoystick.setPosition(object.getRectangle().getX(), object.getRectangle().getY());
            }
        }
    }

    public void setArcadeScreen() {
        MapLayer layer = map.getLayers().get("Screen");
        MapObjects screen = layer.getObjects();
        Array<RectangleMapObject> roomRectangleObjects = screen.getByType(RectangleMapObject.class);

        for(RectangleMapObject object : roomRectangleObjects) {
            screenX = object.getRectangle().getX();
            screenY = object.getRectangle().getY();
            screenHeight = object.getRectangle().getHeight();
            screenWidth = object.getRectangle().getWidth();
        }
    }

    public void drawChosenLevel() {
        host.getBatch().draw(chosenLevel, screenX, screenY, screenWidth, screenHeight);
    }

    public void drawLock() {
        host.getBatch().draw(lock, screenX, screenY, screenWidth, screenHeight);
    }

    public void initStage() {
        stage =  new Stage(new FitViewport(ArcadeMadness.worldWidth, ArcadeMadness.worldHeight), host.getBatch());
        stage.addActor(playButton);
        stage.addActor(settingsButton);
        stage.addActor(leftJoystick);
        stage.addActor(rightJoystick);
    }
    public void addListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(host.isFirstTime() && currentLevel <= unlockedLevels) {
                    MainMenuScreen.this.host.setScreen(new TutorialScreen(host, currentLevel));
                } else if(currentLevel == 1 && unlockedLevels >= 1) {
                    MainMenuScreen.this.host.setScreen(new Level1(MainMenuScreen.this.host));
                } else if(currentLevel == 2 && unlockedLevels >= 2) {
                    MainMenuScreen.this.host.setScreen(new Level2(MainMenuScreen.this.host));
                } else if(currentLevel == 3 && unlockedLevels >= 3) {
                    MainMenuScreen.this.host.setScreen(new Level3(MainMenuScreen.this.host));
                } else if(currentLevel == 4 && unlockedLevels >= 4) {
                    MainMenuScreen.this.host.setScreen(new Level4(MainMenuScreen.this.host));
                }

                host.setNextLevel(currentLevel);

                playSound.play();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //GameEndScreen.this.getHost().setScreen(new MainMenuScreen(GameEndScreen.this.getHost()));
                menuSound.play();
            }
        });

        leftJoystick.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentLevel--;

                if(currentLevel==0) {
                    currentLevel = 1;
                }

                chosenLevel = new Texture("LevelPreviews/level" + currentLevel + "-preview.png");

                menuSound.play();
            }
        });

        rightJoystick.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentLevel++;

                if(currentLevel==5) {
                    currentLevel = 4;
                }


                chosenLevel = new Texture("LevelPreviews/level" + currentLevel + "-preview.png");

                menuSound.play();
            }
        });
    }

}
