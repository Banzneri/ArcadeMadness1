package com.mygdx.arcademadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Banzneri on 17/03/2017.
 */

public class MyInputProcessor implements InputProcessor, GestureDetector.GestureListener {
    com.mygdx.arcademadness.Screens.GameScreen host;
    private boolean isFling = false;
    private boolean isTouchDown = false;

    public MyInputProcessor(com.mygdx.arcademadness.Screens.GameScreen host) {
        this.host = host;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float arrowX = host.getArrowX();
        float arrowY = host.getArrowY();

        if(!Gdx.input.isTouched()) {
            if (Math.abs(screenX - host.getTouchDownX()) > Math.abs(screenY - host.getTouchDownY()) && screenX > host.getTouchDownX() && !host.isArrow("right")) {
                host.addArrow("right", arrowX, arrowY);
            } else if (Math.abs(screenX - host.getTouchDownX()) > Math.abs(screenY - host.getTouchDownY()) && screenX < host.getTouchDownX() && !host.isArrow("left")) {
                host.addArrow("left", arrowX, arrowY);
            } else if (Math.abs(screenX - host.getTouchDownX()) < Math.abs(screenY - host.getTouchDownY()) && screenY > host.getTouchDownY() && !host.isArrow("down")) {
                host.addArrow("down", arrowX, arrowY);
            } else if (Math.abs(screenX - host.getTouchDownX()) < Math.abs(screenY - host.getTouchDownY()) && screenY < host.getTouchDownY() && !host.isArrow("up")) {
                host.addArrow("up", arrowX, arrowY);
            }
        }

        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        // Sets the arrow coordinates when the users finger touches down on the screen.
        // The arrowX and arrowY parameters are used in the fling() method, where the actual placement
        // of the arrow happens.
        int realY = Gdx.input.getY();
        int realX = Gdx.input.getX();
        host.setTouchDownX(Gdx.input.getX());
        host.setTouchDownY(Gdx.input.getY());

        Vector3 touchPos = new Vector3(realX, realY, 0);

        host.getHost().getCamera().unproject(touchPos);

        host.setArrowX(touchPos.x);
        host.setArrowY(touchPos.y);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(!Gdx.input.isTouched()) {
            if (count == 2) {
                host.removeAllArrows();
            } else {
                host.removeArrow(x, y);
            }
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {
    }

}
