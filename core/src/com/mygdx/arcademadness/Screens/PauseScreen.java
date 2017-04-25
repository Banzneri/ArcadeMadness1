package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 11/04/2017.
 */

public class PauseScreen extends MenuScreen {
    private GameScreen currentGameScreen;

    public PauseScreen(ArcadeMadness host, GameScreen currentGameScreen) {

        super(host, new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/play-again.png")))),
                new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/main-menu.png")))));

        this.currentGameScreen = currentGameScreen;
        addListeners();
    }

    public void addListeners() {
        getImageButtonUp().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PauseScreen.this.getHost().setScreen(new MainMenuScreen(PauseScreen.this.getHost()));
            }
        });

        getImageButtonDown().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentGameScreen.setPaused(false);
                PauseScreen.this.getHost().setScreen(currentGameScreen);
            }
        });
    }
}

