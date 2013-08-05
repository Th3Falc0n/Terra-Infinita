package com.dafttech.terra.graphics.gui.containers;

import java.util.ArrayList;
import java.util.List;

import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.event.Events;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.gui.GUIObject;
import com.dafttech.terra.graphics.gui.elements.GUIElement;
import com.dafttech.terra.world.Vector2;

public abstract class GUIContainer extends GUIObject {
    public GUIContainer(Vector2 p, Vector2 s) {
        super(p, s);
    }

    List<GUIElement> elements = new ArrayList<GUIElement>();

    public void draw(AbstractScreen screen) {
        for (GUIElement e : elements) {
            e.draw(screen, position);
        }
    }

    public void addElement(GUIElement element) {
        elements.add(0, element);
    }

    public void addElement(GUIElement element, int index) {
        elements.add(index, element);
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        for (GUIElement e : elements) {
            e.update(delta);
        }
    }

    @EventListener(events = { "MOUSEMOVE" })
    public void onEventMouseMove(Event event) {
        for (GUIElement e : elements) {
            int x = (int) event.getInput()[1];
            int y = (int) event.getInput()[2];

            if (x > e.position.x && x < e.position.x + e.size.x && y > e.position.y && y < e.position.y + e.size.y && !e.mouseHover) {
                e.onMouseIn();
                e.mouseHover = true;
            } else if (!(x > e.position.x && x < e.position.x + e.size.x && y > e.position.y && y < e.position.y + e.size.y) && e.mouseHover) {
                e.onMouseOut();
                e.mouseHover = false;
            }
        }
    }

    @EventListener(events = { "MOUSEDOWN" })
    public void onEventMouseDown(Event event) {
        for (GUIElement e : elements) {
            int button = (int) event.getInput()[0];
            int x = (int) event.getInput()[1];
            int y = (int) event.getInput()[2];

            if (x > e.position.x && x < e.position.x + e.size.x && y > e.position.y && y < e.position.y + e.size.y) {
                e.onClick(button);
            }
        }
    }
}
