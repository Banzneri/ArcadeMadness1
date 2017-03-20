package com.mygdx.arcademadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class MainMenuScreen implements Screen {
    private ArcadeMadness host;
    private Stage stage;
    private OrthographicCamera camera;
    private TextButton startButton;
    private TextButton highscoreButton;
    private Skin skin;

    public MainMenuScreen(ArcadeMadness host) {
        this.host = host;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        createStartButton();
        createHighscoreButton();

        initStage();
        addListeners();

        Gdx.input.setInputProcessor(stage);
    }

    public void createStartButton() {
        startButton = new TextButton("Start game", skin);
        startButton.setSize(100, 50);
        startButton.setPosition(ArcadeMadness.worldWidth / 2 - startButton.getWidth() / 2,
                ArcadeMadness.worldHeight / 2);
    }

    public void createHighscoreButton() {
        highscoreButton = new TextButton("Highscore", skin);
        highscoreButton.setSize(100, 50);
        highscoreButton.setPosition(ArcadeMadness.worldWidth / 2 - highscoreButton.getWidth() / 2,
                ArcadeMadness.worldHeight / 2 - highscoreButton.getHeight() - 20);
    }

    public void initStage() {
        stage =  new Stage(new FitViewport(ArcadeMadness.worldWidth, ArcadeMadness.worldHeight), host.getBatch());
        stage.addActor(startButton);
        stage.addActor(highscoreButton);
    }

    public void addListeners() {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenuScreen.this.host.setScreen(new Level1(MainMenuScreen.this.host));
            }
        });

        highscoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        host.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

    }
}
