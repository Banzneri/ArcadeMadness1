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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 23/03/2017.
 */

/**
 * An abstract MenuScreen class, which implements the Screen interface. All the menu screen classes
 * extend this class.
 */
public abstract class MenuScreen implements Screen {
    public static final Skin SKIN = new Skin(Gdx.files.internal("skin/ui-skin.json"));

    private ArcadeMadness host;
    private Stage stage;
    private OrthographicCamera camera;

    private TextButton textButtonUp;
    private TextButton textButtonDown;

    /**
     * Constructor for the MenuScreen abstract class. Receives the ArcadeMadness host game object,
     * and two ImageButton objects
     *
     * @param host ArcadeMadness object
     */
    public MenuScreen(ArcadeMadness host, TextButton textButtonUp, TextButton textButtonDown) {
        this.host = host;
        this.textButtonUp = textButtonUp;
        this.textButtonDown = textButtonDown;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        setButtons();
        initStage();

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Getter for the ArcadeMadness host object
     *
     * @return ArcadeMadness host object
     */
    public ArcadeMadness getHost() {
        return host;
    }

    public TextButton getTextButtonUp() {
        return textButtonUp;
    }

    public TextButton getTextButtonDown() {
        return textButtonDown;
    }

    public void setButtons() {
        textButtonUp.setWidth(300);
        textButtonUp.setHeight(100);
        textButtonDown.setWidth(300);
        textButtonDown.setHeight(100);

        textButtonUp.setPosition(ArcadeMadness.worldWidth / 2 - textButtonUp.getWidth() / 2, ArcadeMadness.worldHeight / 2);
        textButtonDown.setPosition(ArcadeMadness.worldWidth / 2 - textButtonDown.getWidth() / 2, ArcadeMadness.worldHeight / 2 - 100);
    }

    /**
     * Creates the stage, and adds the button actors to the stage.
     */
    public void initStage() {
        stage =  new Stage(new FitViewport(ArcadeMadness.worldWidth, ArcadeMadness.worldHeight), host.getBatch());
        stage.addActor(textButtonUp);
        stage.addActor(textButtonDown);
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
