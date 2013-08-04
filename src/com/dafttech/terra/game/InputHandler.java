package com.dafttech.terra.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.event.Events;

public class InputHandler {
    List<Integer> registeredKeys = new ArrayList<Integer>();    
    Map<Integer, Boolean> keyDown = new HashMap<Integer, Boolean>();
    Map<Integer, Boolean> mouseDown = new HashMap<Integer, Boolean>();
    
    public InputHandler() {
        mouseDown.put(0, false);
        mouseDown.put(1, false);
        mouseDown.put(2, false);
    }
    
    int lx, ly, x, y;
    
    public void update() {
        for(int i : registeredKeys) {
            if(!keyDown.get(i) && Gdx.input.isKeyPressed(i)) {
                keyDown.put(i, true);
                Events.EVENT_KEYDOWN.callSync(i);
            }
            
            if(keyDown.get(i) && !Gdx.input.isKeyPressed(i)) {
                keyDown.put(i, false);
                Events.EVENT_KEYUP.callSync(i);
            }
        }
        
        lx = x;
        ly = y;
        x = Gdx.input.getX();
        y = Gdx.input.getY();
        
        for(int i = 0; i < 2; i++) {
            if((!mouseDown.get(i)) && Gdx.input.isButtonPressed(i)) {
                mouseDown.put(i, true);
                Events.EVENT_MOUSEDOWN.callSync(i, x, y);
            }
            
            if(mouseDown.get(i) && (!Gdx.input.isButtonPressed(i))) {
                mouseDown.put(i, false);
                Events.EVENT_MOUSEUP.callSync(i, x, y);
            }
            
        }
        
        if(lx != x || ly != y) {
            Events.EVENT_MOUSEMOVE.callSync(-1, x, y);
        }
    }
    
    public void registerKey(int key) {
        registeredKeys.add(key);
        keyDown.put(key, false);
    }
}
