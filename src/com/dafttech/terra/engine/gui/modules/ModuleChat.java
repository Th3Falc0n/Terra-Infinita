package com.dafttech.terra.engine.gui.modules;

import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventFilter;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.AnchorBottom;
import com.dafttech.terra.engine.gui.anchors.AnchorLeft;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.engine.gui.containers.ContainerList;
import com.dafttech.terra.engine.gui.elements.ElementInputLabel;
import com.dafttech.terra.engine.gui.elements.ElementLabel;
import com.dafttech.terra.engine.input.handlers.IStringInputHandler;
import com.dafttech.terra.game.Events;

public class ModuleChat extends GUIModule implements IStringInputHandler {
    public ContainerList messageList;
    public ElementInputLabel inputLabel;

    @Override
    public void create() {
        Events.EVENTMANAGER.registerEventListener(this);

        container = new ContainerBlock(new Vector2(), new Vector2(400, 280));

        GUIAnchorSet set = new GUIAnchorSet();

        set.addAnchor(new AnchorLeft(0.01f));
        set.addAnchor(new AnchorBottom(0.01f));

        container.assignAnchorSet(set);

        inputLabel = new ElementInputLabel(new Vector2(10, 250), this);
        messageList = new ContainerList(new Vector2(10, 10), new Vector2(380, 230));

        container.addObject(messageList);
        container.addObject(inputLabel);
    }

    public void addMessage(String msg) {
        messageList.addObject(new ElementLabel(new Vector2(), msg));
    }

    @EventListener(value = "KEYDOWN", filter = "filterOnChatKeyUsed")
    public void onChatKeyUsed(Event event) {
        inputLabel.beginStringInput();
    }

    @EventFilter("filterOnChatKeyUsed")
    public String filter1() {
        return "CHAT";
    }

    @Override
    public void handleInput(String str) {
        addMessage(str);
    }
}
