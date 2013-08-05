package com.dafttech.terra.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.dafttech.terra.event.Events;

public class InputHandler {
    static List<Integer> registeredKeys = new ArrayList<Integer>();
    static Map<String, Integer> keyNames = new HashMap<String, Integer>();
    static Map<Integer, Boolean> keyDown = new HashMap<Integer, Boolean>();
    static Map<Integer, Boolean> mouseDown = new HashMap<Integer, Boolean>();

    public static void init() {
        mouseDown.put(0, false);
        mouseDown.put(1, false);
        mouseDown.put(2, false);

        registerKey(Keys.W, "UP");
        registerKey(Keys.A, "LEFT");
        registerKey(Keys.S, "DOWN");
        registerKey(Keys.D, "RIGHT");

        registerKey(Keys.SPACE, "JUMP");
    }

    public static boolean isKeyDown(String name) {
        return keyDown.get(getKeyID(name));
    }

    public static int getKeyID(String name) {
        if (!keyNames.containsKey(name)) throw new IllegalArgumentException("Key not registered: " + name);
        return keyNames.get(name);
    }

    static int lx, ly, x, y;

    public static void update() {
        for (int i : registeredKeys) {
            if (!keyDown.get(i) && Gdx.input.isKeyPressed(i)) {
                keyDown.put(i, true);
                Events.EVENT_KEYDOWN.callSync(i);
            }

            if (keyDown.get(i) && !Gdx.input.isKeyPressed(i)) {
                keyDown.put(i, false);
                Events.EVENT_KEYUP.callSync(i);
            }
        }

        lx = x;
        ly = y;
        x = Gdx.input.getX();
        y = Gdx.input.getY();

        for (int i = 0; i < 2; i++) {
            if ((!mouseDown.get(i)) && Gdx.input.isButtonPressed(i)) {
                mouseDown.put(i, true);
                Events.EVENT_MOUSEDOWN.callSync(i, x, y);
            }

            if (mouseDown.get(i) && (!Gdx.input.isButtonPressed(i))) {
                mouseDown.put(i, false);
                Events.EVENT_MOUSEUP.callSync(i, x, y);
            }

        }

        if (lx != x || ly != y) {
            Events.EVENT_MOUSEMOVE.callSync(-1, x, y);
        }
    }

    public static void registerKey(int key, String name) {
        if (registeredKeys.contains(key)) {
            Gdx.app.log("InputHandler", "Multiple Key Registration: " + key);
            return;
        }
        keyNames.put(name, key);
        registeredKeys.add(key);
        keyDown.put(key, false);
    }
}
