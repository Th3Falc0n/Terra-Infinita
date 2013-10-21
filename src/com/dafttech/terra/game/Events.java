package com.dafttech.terra.game;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventManager;
import com.dafttech.eventmanager.EventType;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;

public class Events {
    public static final EventManager EVENTMANAGER = new EventManager();

    public static final EventType EVENT_INITPRE = new EventType(EVENTMANAGER, "INITPRE");
    public static final EventType EVENT_INITPOST = new EventType(EVENTMANAGER, "INITPOST");

    public static final EventType EVENT_WINRESIZE = new EventType(EVENTMANAGER, "WINRESIZE");
    public static final EventType EVENT_WINPAUSE = new EventType(EVENTMANAGER, "WINPAUSE");
    public static final EventType EVENT_WINRESUME = new EventType(EVENTMANAGER, "WINRESUME");
    public static final EventType EVENT_WINDISPOSE = new EventType(EVENTMANAGER, "WINDISPOSE");

    public static final EventType EVENT_BLOCKCHANGE = new EventType(EVENTMANAGER, "BLOCKCHANGE");

    public static final EventType EVENT_CHATCOMMAND = new EventType(EVENTMANAGER, "CHATCOMMAND");

    public static final EventType EVENT_KEYDOWN = new EventType(EVENTMANAGER, "KEYDOWN") {
        @Override
        protected boolean applyFilter(Event event, Object[] filter, Object eventListener) {
            return ((String) filter[0]).equals(event.getInput(0, String.class));
        }
    };
    public static final EventType EVENT_KEYUP = new EventType(EVENTMANAGER, "KEYUP") {
        @Override
        protected boolean applyFilter(Event event, Object[] filter, Object eventListener) {
            return ((String) filter[0]).equals(event.getInput(0, String.class));
        }
    };

    public static final EventType EVENT_MOUSEDOWN = new EventType(EVENTMANAGER, "MOUSEDOWN");
    public static final EventType EVENT_MOUSEUP = new EventType(EVENTMANAGER, "MOUSEUP");
    public static final EventType EVENT_MOUSEMOVE = new EventType(EVENTMANAGER, "MOUSEMOVE");
    public static final EventType EVENT_SCROLL = new EventType(EVENTMANAGER, "SCROLL");

    public static final EventType EVENT_BLOCKUPDATE = new EventType(EVENTMANAGER, "BLOCKUPDATE") {
        @Override
        protected void onEvent(Event event) {
            Tile tile = event.getInput(0, World.class).getTile(event.getInput(1, Integer.class), event.getInput(2, Integer.class));
            if (tile != null) tile.update(event.getInput(0, World.class), 0);
        }
    };

    public static final EventType EVENT_WORLDTICK = new EventType(EVENTMANAGER, "GAMETICK") {
        @Override
        protected void onEvent(Event event) {
            World world = event.getInput(0, World.class);
            int sx = 25 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
            int sy = 25 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;
            Tile tile;
            for (int x = (int) world.localPlayer.getPosition().x / BLOCK_SIZE - sx; x < (int) world.localPlayer.getPosition().x / BLOCK_SIZE + sx; x++) {
                for (int y = (int) world.localPlayer.getPosition().y / BLOCK_SIZE - sy; y < (int) world.localPlayer.getPosition().y / BLOCK_SIZE + sy; y++) {
                    tile = world.getTile(x, y);
                    if (tile != null) tile.tick(world);
                }
            }
        }
    };

    public static void init() {
    }
}