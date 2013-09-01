package com.dafttech.terra.game;

import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventManager;
import com.dafttech.eventmanager.EventType;

public class Events {
    public static final EventManager EVENTMANAGER = new EventManager();

    public static final EventType EVENT_INITPRE = new EventType(EVENTMANAGER, "INITPRE");
    public static final EventType EVENT_INITPOST = new EventType(EVENTMANAGER, "INITPOST");

    public static final EventType EVENT_WINRESIZE = new EventType(EVENTMANAGER, "WINRESIZE");
    public static final EventType EVENT_WINPAUSE = new EventType(EVENTMANAGER, "WINPAUSE");
    public static final EventType EVENT_WINRESUME = new EventType(EVENTMANAGER, "WINRESUME");
    public static final EventType EVENT_WINDISPOSE = new EventType(EVENTMANAGER, "WINDISPOSE");

    public static final EventType EVENT_WORLDTICK = new EventType(EVENTMANAGER, "WORLDTICK");
    public static final EventType EVENT_BLOCKCHANGE = new EventType(EVENTMANAGER, "BLOCKCHANGE");

    public static final EventType EVENT_CHATCOMMAND = new EventType(EVENTMANAGER, "CHATCOMMAND");

    public static final EventType EVENT_KEYDOWN = new EventType(EVENTMANAGER, "KEYDOWN") {
        @Override
        protected boolean applyFilter(Event event, Object eventListener, Object[] filter) {
            return ((String) filter[0]).equals(event.getInput(0, String.class));
        }
    };
    public static final EventType EVENT_KEYUP = new EventType(EVENTMANAGER, "KEYUP") {
        @Override
        protected boolean applyFilter(Event event, Object eventListener, Object[] filter) {
            return ((String) filter[0]).equals(event.getInput(0, String.class));
        }
    };

    public static final EventType EVENT_MOUSEDOWN = new EventType(EVENTMANAGER, "MOUSEDOWN");
    public static final EventType EVENT_MOUSEUP = new EventType(EVENTMANAGER, "MOUSEUP");
    public static final EventType EVENT_MOUSEMOVE = new EventType(EVENTMANAGER, "MOUSEMOVE");

    public static void init() {
    }
}