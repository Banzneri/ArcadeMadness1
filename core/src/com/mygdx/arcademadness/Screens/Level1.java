package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.arcademadness.*;

/**
 * Created by Banzneri on 16/03/2017.
 */


/**
 * The level 1 class. Extends the GameScreen class
 */
public class Level1 extends GameScreen {

    /**
     * The constructor for the Level1 class. Uses the super constructor of the GameScreen class.
     * The super constructor receives the corresponding tiled map, the spawn interval in seconds for the characters, and
     * the ArcadeMadness host game object
     *
     * @param host The ArcadeMadness host game object
     */
    public Level1(ArcadeMadness host) {
        super("TiledMaps/level1small.tmx", 7, host);
    }

    @Override
    public void show() {

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
}
