package com.dafttech.terra.event;

import com.dafttech.eventmanager.EventManager;
import com.dafttech.eventmanager.EventType;

public class Events {
    public static Events instance = new Events();

    public static final EventManager EVENTMANAGER = new EventManager();

    public static final EventType EVENT_INITPRE = new EventType(EVENTMANAGER);

    public static final EventType EVENT_INITPOST = new EventType(EVENTMANAGER);

    public static final EventType EVENT_WINRESIZE = new EventType(EVENTMANAGER);

    public static final EventType EVENT_WINPAUSE = new EventType(EVENTMANAGER);

    public static final EventType EVENT_WINRESUME = new EventType(EVENTMANAGER);

    public static final EventType EVENT_WINDISPOSE = new EventType(EVENTMANAGER);

    public static final EventType EVENT_WORLDTICK = new EventType(EVENTMANAGER);

    public static final EventType EVENT_BLOCKNEIGHBORCHANGE = new EventType(EVENTMANAGER);

    public void init() {

    }
}