package com.dafttech.terra.engine.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.dafttech.terra.game.Events;

public class InputHandler implements InputProcessor {
    List<Integer> registeredKeys = new ArrayList<Integer>();
    Map<String, Integer> keyNames = new HashMap<String, Integer>();
    Map<Integer, Boolean> keyDown = new HashMap<Integer, Boolean>();

    public static InputHandler $ = new InputHandler();

    public static void init() {
        $.initialize();
    }

    public void initialize() {
        Gdx.input.setInputProcessor(this);

        registerKey(Keys.W, "UP");
        registerKey(Keys.A, "LEFT");
        registerKey(Keys.S, "DOWN");
        registerKey(Keys.D, "RIGHT");

        registerKey(Keys.SPACE, "JUMP");
    }

    public boolean isKeyDown(String name) {
        if (!keyDown.containsKey(getKeyID(name))) return false;
        return keyDown.get(getKeyID(name));
    }

    public int getKeyID(String name) {
        if (!keyNames.containsKey(name)) throw new IllegalArgumentException("Key not registered: " + name);
        return keyNames.get(name);
    }

    public void registerKey(int key, String name) {
        if (registeredKeys.contains(key)) {
            Gdx.app.log("InputHandler", "Multiple Key Registration: " + key);
            return;
        }
        keyNames.put(name, key);
    }

    @Override
    public boolean keyDown(int i) {
        if (FocusManager.typeFocusAssigned()) {
            FocusManager.typeFocus.onKeyDown(i);
            return true;
        }
        keyDown.put(i, true);
        Events.EVENT_KEYDOWN.callSync(i);
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        if (FocusManager.typeFocusAssigned()) {
            FocusManager.typeFocus.onKeyTyped(c);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        if (FocusManager.typeFocusAssigned()) return false;
        keyDown.put(i, false);
        Events.EVENT_KEYUP.callSync(i);

        return true;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        Events.EVENT_MOUSEMOVE.callSync(-1, x, y);
        return true;
    }

    @Override
    public boolean scrolled(int arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int p, int b) {
        Events.EVENT_MOUSEDOWN.callSync(b, x, y);
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int p, int b) {
        Events.EVENT_MOUSEUP.callSync(b, x, y);
        return true;
    }
}
