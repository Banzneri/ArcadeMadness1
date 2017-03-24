package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 23/03/2017.
 */

public abstract class MenuScreen implements Screen {
    private ArcadeMadness host;
    private Stage stage;
    private OrthographicCamera camera;

    private Skin skin;

    private ImageButton imageButtonUp;
    private ImageButton imageButtonDown;

    public MenuScreen(ArcadeMadness host, ImageButton imageButtonUp, ImageButton imageButtonDown) {
        this.host = host;
        this.imageButtonUp = imageButtonUp;
        this.imageButtonDown = imageButtonDown;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        createUpButton();
        createDownButton();
        initStage();

        Gdx.input.setInputProcessor(stage);
    }

    public ArcadeMadness getHost() {
        return host;
    }

    public void setImageButtonUp(ImageButton button) {
        imageButtonUp = button;
    }

    public void setImageButtonDown(ImageButton button) {
        imageButtonDown = button;
    }

    public ImageButton getImageButtonUp() {
        return imageButtonUp;
    }

    public ImageButton getImageButtonDown() {
        return imageButtonDown;
    }

    public void initStage() {
        stage =  new Stage(new FitViewport(ArcadeMadness.worldWidth, ArcadeMadness.worldHeight), host.getBatch());
        stage.addActor(imageButtonUp);
        stage.addActor(imageButtonDown);
    }

    public void createUpButton() {
        imageButtonUp.setSize(200, 100);
        imageButtonUp.setPosition(ArcadeMadness.worldWidth / 2 - imageButtonUp.getWidth() / 2,
                ArcadeMadness.worldHeight / 2);
    }

    public void createDownButton() {
        imageButtonDown.setSize(200, 100);
        imageButtonDown.setPosition(ArcadeMadness.worldWidth / 2 - imageButtonDown.getWidth() / 2,
                ArcadeMadness.worldHeight / 2 - imageButtonDown.getHeight() - 20);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
