package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 23/03/2017.
 */

public class IntroScreen implements Screen{
    private ArcadeMadness host;
    private OrthographicCamera camera;
    private float counter = 0;

    public IntroScreen(ArcadeMadness host) {
        this.host = host;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        host.getBatch().setProjectionMatrix(camera.combined);
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawLogo();

        if(Gdx.input.isTouched()) {
            host.setScreen(new MainMenuScreen(host));
        }
    }

    public void drawLogo() {
        counter += Gdx.graphics.getDeltaTime();
        host.getBatch().begin();
        host.getBatch().draw(host.getLogo().getKeyFrame(counter, true), ArcadeMadness.worldWidth / 2 - 100, ArcadeMadness.worldHeight / 2 - 120, 400, 400);
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

    }
}
