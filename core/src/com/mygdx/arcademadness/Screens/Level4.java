package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 10/04/2017.
 */

public class Level4 extends GameScreen{
    private TiledMap map;

    /**
     * The constructor for the Level4 class. Uses the super constructor of the GameScreen class.
     * The super constructor receives the corresponding tiled map, the spawn interval in seconds for the characters, and
     * the ArcadeMadness host game object
     *
     * @param host The ArcadeMadness host game object
     */
    public Level4(ArcadeMadness host) {
        super("TiledMaps/level4small.tmx", 5, host);
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
