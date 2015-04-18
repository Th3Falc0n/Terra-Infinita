package com.dafttech.terra.engine.gui.containers;

import java.util.ArrayList;
import java.util.List;

import org.lolhens.eventmanager.Event;
import org.lolhens.eventmanager.EventListener;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.GUIObject;

public abstract class GUIContainer extends GUIObject {
    public GUIContainer(Vector2 p, Vector2 s) {
        super(p, s);
    }

    List<GUIObject> objects = new ArrayList<GUIObject>();

    @Override
    public void draw(AbstractScreen screen) {
        for (GUIObject o : objects) {
            o.draw(screen);
        }
    }

    public void clearObjects() {
        objects.clear();
    }

    public boolean containsObject(GUIObject object) {
        return objects.contains(object);
    }

    public void addObject(GUIObject object) {
        addObject(object, 0);
    }

    public void addObject(GUIObject object, int index) {
        objects.add(index, object);
        object.setContainer(this);

        object.applyAssignedAnchorSet();
    }

    public void removeObject(int index) {
        objects.get(index).setContainer(null);
        objects.remove(index);
    }

    public void removeObject(GUIObject object) {
        if (objects.contains(object)) removeObject(objects.indexOf(object));
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        for (GUIObject e : objects) {
            e.update(delta);
        }
    }

    public void applyAllAssignedAnchorSets() {
        applyAssignedAnchorSet();
        for (GUIObject o : objects) {
            if (o instanceof GUIContainer) {
                ((GUIContainer) o).applyAllAssignedAnchorSets();
            }
            o.applyAssignedAnchorSet();
        }
    }

    @EventListener("MOUSEMOVE")
    public void onEventMouseMove(Event event) {
        if (isInActiveHierarchy() || this.providesActiveHierarchy()) {
            for (GUIObject e : objects) {
                int x = event.in.get(1, Integer.class);
                int y = event.in.get(2, Integer.class);

                Vector2 p = e.getScreenPosition();

                if (e.size != null) {
                    if (x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y && !e.mouseHover) {
                        e.onMouseIn();
                        e.mouseHover = true;
                    } else if (!(x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y) && e.mouseHover) {
                        e.onMouseOut();
                        e.mouseHover = false;
                    }
                } else {
                    System.out.println("Size null in " + e);
                }
            }
        }
    }

    @EventListener("MOUSEDOWN")
    public void onEventMouseDown(Event event) {
        if (isInActiveHierarchy() || this.providesActiveHierarchy()) {
            for (GUIObject e : objects) {
                int button = event.in.get(0, Integer.class);
                int x = event.in.get(1, Integer.class);
                int y = event.in.get(2, Integer.class);

                Vector2 p = e.getScreenPosition();

                if (e.size != null) {
                    if (x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y) {
                        e.onClick(button);
                    }
                } else {
                    System.out.println("Size null in " + e);
                }
            }
        }
    }
}
