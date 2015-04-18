package com.dafttech.terra.game;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;
import org.lolhens.eventmanager.Event;
import org.lolhens.eventmanager.EventManager;
import org.lolhens.eventmanager.EventType;
import org.lolhens.eventmanager.ListenerContainer;
import org.lolhens.storage.tuple.Tuple;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

public class Events {
    public static final EventManager EVENTMANAGER = new EventManager();

    public static final EventType EVENT_INITPRE = new EventType("INITPRE", EVENTMANAGER);
    public static final EventType EVENT_INITPOST = new EventType("INITPOST", EVENTMANAGER);

    public static final EventType EVENT_WINRESIZE = new EventType("WINRESIZE", EVENTMANAGER);
    public static final EventType EVENT_WINPAUSE = new EventType("WINPAUSE", EVENTMANAGER);
    public static final EventType EVENT_WINRESUME = new EventType("WINRESUME", EVENTMANAGER);
    public static final EventType EVENT_WINDISPOSE = new EventType("WINDISPOSE", EVENTMANAGER);

    public static final EventType EVENT_BLOCKCHANGE = new EventType("BLOCKCHANGE", EVENTMANAGER);

    public static final EventType EVENT_CHATCOMMAND = new EventType("CHATCOMMAND", EVENTMANAGER);

    public static final EventType EVENT_KEYDOWN = new EventType("KEYDOWN", EVENTMANAGER) {
        @Override
        protected boolean isFiltered(Event event, Tuple filter, ListenerContainer eventListener) {
            return filter.get(0, String.class).equals(event.in.get(0, String.class));
        }
    };
    public static final EventType EVENT_KEYUP = new EventType("KEYUP", EVENTMANAGER) {
        @Override
        protected boolean isFiltered(Event event, Tuple filter, ListenerContainer eventListener) {
            return filter.get(0, String.class).equals(event.in.get(0, String.class));
        }
    };

    public static final EventType EVENT_MOUSEDOWN = new EventType("MOUSEDOWN", EVENTMANAGER);
    public static final EventType EVENT_MOUSEUP = new EventType("MOUSEUP", EVENTMANAGER);
    public static final EventType EVENT_MOUSEMOVE = new EventType("MOUSEMOVE", EVENTMANAGER);
    public static final EventType EVENT_SCROLL = new EventType("SCROLL", EVENTMANAGER);

    public static final EventType EVENT_BLOCKUPDATE = new EventType("BLOCKUPDATE", EVENTMANAGER) {
        @Override
        protected void onEvent(Event event) {
            Tile tile = event.in.get(0, World.class).getTile(event.in.get(1, Integer.class), event.in.get(2, Integer.class));
            if (tile != null) tile.update(event.in.get(0, World.class), 0);
        }
    };

    public static final EventType EVENT_WORLDTICK = new EventType("GAMETICK", EVENTMANAGER) {
        @Override
        protected void onEvent(Event event) {
            World world = event.in.get(0, World.class);
            int sx = 25 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
            int sy = 25 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;
            Tile tile;
            for (int x = (int) world.localPlayer.getPosition().x / BLOCK_SIZE - sx; x < (int) world.localPlayer.getPosition().x / BLOCK_SIZE + sx; x++) {
                for (int y = (int) world.localPlayer.getPosition().y / BLOCK_SIZE - sy; y < (int) world.localPlayer.getPosition().y / BLOCK_SIZE + sy; y++) {
                    tile = world.getTile(x, y);
                    if (tile != null) tile.tick(world, event.in.get(1, Float.class));
                }
            }
        }
    };

    public static void init() {
    }
}