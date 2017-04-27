package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 11/04/2017.
 */

public class PauseScreen extends MenuScreen {
    private GameScreen currentGameScreen;

    public PauseScreen(ArcadeMadness host, GameScreen currentGameScreen) {

        super(host, new TextButton("Continue", MenuScreen.SKIN),
                    new TextButton("Return to main menu", MenuScreen.SKIN));

        this.currentGameScreen = currentGameScreen;
        addListeners();
    }

    public void addListeners() {
        getTextButtonUp().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentGameScreen.setPaused(false);
                PauseScreen.this.currentGameScreen.initInputProcessor();
                PauseScreen.this.getHost().setScreen(currentGameScreen);
            }
        });

        getTextButtonDown().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PauseScreen.this.getHost().setScreen(new MainMenuScreen(PauseScreen.this.getHost()));
            }
        });
    }
}

