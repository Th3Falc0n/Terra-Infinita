package com.dafttech.terra.engine.gui.containers;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.GUIObject;

public class ContainerList extends GUIContainer {
    float distance = 2;

    public ContainerList(Vector2 p, Vector2 s) {
        super(p, s);
    }

    public ContainerList(Vector2 p, Vector2 s, float d) {
        super(p, s);
        distance = d;
    }

    @Override
    public void draw(AbstractScreen screen) {

        float y = size.y;

        for (GUIObject o : objects) {
            y -= o.size.y + distance;

            if (y > 0) {
                o.position = new Vector2(0, y);
                o.draw(screen);
            }
        }
    }
}
