package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.arcademadness.*;

/**
 * Created by Banzneri on 16/03/2017.
 */

public class MainMenuScreen extends MenuScreen {

    public MainMenuScreen(ArcadeMadness host) {
        super(host, new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("save-game.png")))),
                    new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("highscore.png")))));

        addListeners();
    }

    public void addListeners() {
        getImageButtonUp().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenuScreen.this.getHost().setScreen(new Level1(MainMenuScreen.this.getHost()));
            }
        });

        getImageButtonDown().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
    }
}
