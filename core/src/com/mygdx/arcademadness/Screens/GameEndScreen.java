package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 23/03/2017.
 */

public class GameEndScreen extends MenuScreen {

    public GameEndScreen(ArcadeMadness host) {
        super(host, new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("play-again.png")))),
                    new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("main-menu.png")))));

        addListeners();
    }

    public void addListeners() {
        getImageButtonUp().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameEndScreen.this.getHost().setScreen(new Level1(GameEndScreen.this.getHost()));
            }
        });

        getImageButtonDown().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameEndScreen.this.getHost().setScreen(new MainMenuScreen(GameEndScreen.this.getHost()));
            }
        });
    }
}
