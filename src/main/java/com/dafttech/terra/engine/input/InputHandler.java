package com.dafttech.terra.engine.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.dafttech.terra.game.Events;

public class InputHandler implements InputProcessor {
    Map<String, Integer> keyNames = new HashMap<String, Integer>();
    Map<Integer, String> keyIds = new HashMap<Integer, String>();
    Map<Integer, Boolean> keyDown = new HashMap<Integer, Boolean>();

    public static InputHandler $ = new InputHandler();

    public static void init() {
        $.initialize();
    }

    public void initialize() {
        Gdx.input.setInputProcessor(this);

        registerKey(Keys.ESCAPE, "PAUSE");

        registerKey(Keys.W, "UP");
        registerKey(Keys.A, "LEFT");
        registerKey(Keys.S, "DOWN");
        registerKey(Keys.D, "RIGHT");
        registerKey(Keys.N, "WAVESLEFT");
        registerKey(Keys.M, "WAVESRIGHT");

        registerKey(Keys.E, "INVENTORY");
        registerKey(Keys.C, "CRAFTING");

        registerKey(Keys.SPACE, "JUMP");
        registerKey(Keys.ENTER, "CHAT");
    }

    public boolean isKeyDown(String name) {
        if (!keyDown.containsKey(getKeyID(name))) return false;
        return keyDown.get(getKeyID(name));
    }

    public boolean isKeyRegistered(int id) {
        return keyIds.containsKey(id);
    }

    public boolean isKeyRegistered(String name) {
        return keyNames.containsKey(name);
    }

    public int getKeyID(String name) {
        if (!keyNames.containsKey(name)) throw new IllegalArgumentException("Key not registered: " + name);
        return keyNames.get(name);
    }

    public String getKeyName(int id) {
        if (!keyIds.containsKey(id)) throw new IllegalArgumentException("Key not registered: " + id);
        return keyIds.get(id);
    }

    public void registerKey(int key, String name) {
        if (isKeyRegistered(key) || isKeyRegistered(name)) {
            Gdx.app.log("InputHandler", "Multiple Key Registration: " + key);
            return;
        }
        keyNames.put(name, key);
        keyIds.put(key, name);
    }

    @Override
    public boolean keyDown(int i) {
        if (FocusManager.typeFocusAssigned()) {
            FocusManager.typeFocus.onKeyDown(i);
            return true;
        }
        keyDown.put(i, true);
        if (isKeyRegistered(i)) Events.EVENTMANAGER.callSync(Events.EVENT_KEYDOWN, getKeyName(i));
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
        if (isKeyRegistered(i)) Events.EVENTMANAGER.callSync(Events.EVENT_KEYUP, getKeyName(i));
        return true;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        Events.EVENTMANAGER.callSync(Events.EVENT_MOUSEMOVE, -1, x, y);
        return true;
    }

    @Override
    public boolean scrolled(int arg0) {
        Events.EVENTMANAGER.callSync(Events.EVENT_SCROLL, arg0);
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int p, int b) {
        Events.EVENTMANAGER.callSync(Events.EVENT_MOUSEDOWN, b, x, y);
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int p, int b) {
        Events.EVENTMANAGER.callSync(Events.EVENT_MOUSEUP, b, x, y);
        return true;
    }
}
