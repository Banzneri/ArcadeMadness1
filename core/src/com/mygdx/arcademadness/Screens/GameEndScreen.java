package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 23/03/2017.
 */

/**
 * Menu screen class, which comes when the game ends. Has two buttons: play again, and return to main menu.
 * Extends the abstract MenuScreen object
 */
public class GameEndScreen extends MenuScreen {

    /**
     * Constructor for the GameEndScreen class. Uses the super constructor of the MenuScreen class, which
     * receives ArcadeMadness host object and two ImageButton objects
     *
     * Also adds menu specific listeners for the buttons
     *
     * @param host ArcadeMadness host object
     */
    public GameEndScreen(ArcadeMadness host, Boolean won) {
        super(host);

        if(won) {
            setWon("won");
            setTitleText("Level " + (host.getNextLevel() - 1));
            setSubtitleText("You won!");

            setUpperButtonText("Next level");
            setLowerButtonText("Return to main menu");
        } else {
            setWon("lost");
            setTitleText("Level " + host.getNextLevel());
            setSubtitleText("You lost!");

            setUpperButtonText("Try again");
            setLowerButtonText("Return to main menu");
        }



        addListeners();
    }

    /**
     * Adds listeners for the buttons, one starts the next level (or the same if the player lost) and
     * the other returns to the main menu.
     */
    public void addListeners() {
        getTextButtonUp().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameEndScreen.this.getHost().setScreen(getHost().getNextLevelObject());
            }
        });

        getTextButtonDown().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameEndScreen.this.getHost().setScreen(new MainMenuScreen(GameEndScreen.this.getHost()));
            }
        });
    }
}
