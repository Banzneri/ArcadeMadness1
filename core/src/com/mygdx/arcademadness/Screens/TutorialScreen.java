package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 07/05/2017.
 */

public class TutorialScreen implements Screen {
    private ArcadeMadness host;

    private boolean pressed = false;
    private boolean pressedTwice = false;
    private boolean pressedThrice = false;

    private Animation<TextureRegion> tutorialAnim1;
    private Animation<TextureRegion> tutorialAnim2;
    private Texture tutorialText = new Texture("tutorial3-en.png");
    private float frameCounter1 = 0;
    private float frameCounter2 = 0;

    public TutorialScreen(ArcadeMadness host) {
        this.host = host;
        tutorialAnim1 = host.getIntroAnim1();
        tutorialAnim2 = host.getIntroAnim2();

        Gdx.input.setInputProcessor(new InputAdapter());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        host.getBatch().begin();

        if(!pressed) {
            frameCounter1 += Gdx.graphics.getDeltaTime();
            host.getBatch().draw(tutorialAnim1.getKeyFrame(frameCounter1, true), 0, 0, ArcadeMadness.worldWidth, ArcadeMadness.worldHeight);
        } else {
            frameCounter2 += Gdx.graphics.getDeltaTime();
            host.getBatch().draw(tutorialAnim2.getKeyFrame(frameCounter2, true), 0, 0, ArcadeMadness.worldWidth, ArcadeMadness.worldHeight);
        }

        if(pressedTwice) {
            host.getBatch().draw(tutorialText, 0, 0, ArcadeMadness.worldWidth, ArcadeMadness.worldHeight);
        }

        if(pressedThrice) {
            updatePreferences();
            host.setScreen(new Level1(host));
        }

        host.getBatch().end();

        managePresses();
    }

    public void managePresses() {
        if(Gdx.input.justTouched()) {
            if(pressedTwice) {
                pressedThrice = true;
            } else if(pressed) {
                pressedTwice = true;
            } else {
                pressed = true;
            }
        }
    }

    public void updatePreferences() {
        Preferences prefs = Gdx.app.getPreferences("ArcadeMadnessPrefs");
        prefs.putBoolean("firstTime", false);
        prefs.flush();
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
