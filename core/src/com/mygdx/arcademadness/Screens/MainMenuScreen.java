package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

    public MainMenuScreen(ArcadeMadness host) {
        this.host = host;
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("main-menu.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        chosenLevel = new Texture(Gdx.files.internal("level1.png"));

        setArcadeScreen();
        setButtons();
        addListeners();
        initStage();

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

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
                playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("play-button.png"))));
                playButton.setSize(object.getRectangle().getWidth(), object.getRectangle().getHeight());
                playButton.setPosition(object.getRectangle().getX(), object.getRectangle().getY());
            } else if(object.getName().equals("settings-button")) {
                settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("settings-button.png"))));
                settingsButton.setSize(object.getRectangle().getWidth(), object.getRectangle().getHeight());
                settingsButton.setPosition(object.getRectangle().getX(), object.getRectangle().getY());
            } else if(object.getName().equals("left-stick")) {
                leftJoystick = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("joystick.png"))));
                leftJoystick.setSize(32, 64);
                leftJoystick.setPosition(object.getRectangle().getX(), object.getRectangle().getY() - object.getRectangle().getHeight());
            } else if(object.getName().equals("right-stick")) {
                rightJoystick = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("joystick.png"))));
                rightJoystick.setSize(32, 64);
                rightJoystick.setPosition(object.getRectangle().getX(), object.getRectangle().getY() - object.getRectangle().getHeight());
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
                if(currentLevel == 1) {
                    MainMenuScreen.this.host.setScreen(new Level1(MainMenuScreen.this.host));
                } else if(currentLevel == 2) {
                    MainMenuScreen.this.host.setScreen(new Level2(MainMenuScreen.this.host));
                } else if(currentLevel == 3) {
                    MainMenuScreen.this.host.setScreen(new Level3(MainMenuScreen.this.host));
                }
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //GameEndScreen.this.getHost().setScreen(new MainMenuScreen(GameEndScreen.this.getHost()));
            }
        });

        leftJoystick.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentLevel--;

                if(currentLevel==0) {
                    currentLevel = 1;
                }

                chosenLevel = new Texture("level" + currentLevel + ".png");
            }
        });

        rightJoystick.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentLevel++;

                if(currentLevel==4) {
                    currentLevel = 3;
                }

                chosenLevel = new Texture("level" + currentLevel + ".png");
                //MainMenuScreen.this.host.setScreen(new MainMenuScreen(GameEndScreen.this.getHost()));
            }
        });
    }

}
