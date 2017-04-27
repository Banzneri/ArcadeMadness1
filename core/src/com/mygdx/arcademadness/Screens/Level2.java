package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 02/04/2017.
 */

public class Level2 extends GameScreen {
    private TiledMap map;

    /**
     * The constructor for the Level2 class. Uses the super constructor of the GameScreen class.
     * The super constructor receives the corresponding tiled map, the spawn interval in seconds for the characters, and
     * the ArcadeMadness host game object
     *
     * @param host The ArcadeMadness host game object
     */
    public Level2(ArcadeMadness host) {
        super("TiledMaps/level2small.tmx", 7, host);
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

