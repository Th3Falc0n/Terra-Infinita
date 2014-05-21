package com.dafttech.terra.engine.gui.modules;

import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.AnchorBottom;
import com.dafttech.terra.engine.gui.anchors.AnchorCenterX;
import com.dafttech.terra.engine.gui.anchors.AnchorLeft;
import com.dafttech.terra.engine.gui.anchors.AnchorTop;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.engine.gui.containers.ContainerList;
import com.dafttech.terra.engine.gui.elements.ElementLabel;
import com.dafttech.terra.engine.gui.elements.ElementSlot;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.items.inventories.Inventory;

public class ModuleInventory extends GUIModule {
    ContainerList invList;
    
    Inventory inv = null;
    int index = 0;
    
    public ModuleInventory(Inventory i) {
        inv = i;
    }

    @EventListener("SCROLL")
    public void onScroll(Event event) {
        if(invList.mouseHover) {
            int i = event.getInput(0, Integer.class);
            
            index += (i == 0) ? -1 : 1;
            
            if(index < 0) {
                index = 0;
            }
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        
        invList.clearObjects();
        
        System.out.println("INVTEST: " + inv.getList().size());
        
        for(int n = 0; n < 5; n++) {
            if(index + n < inv.getList().size()) {
                ElementSlot slot = new ElementSlot(new Vector2());
    
                slot.assignStack(inv.getList().get(index + n));
                invList.addObject(slot);
            }
        }
        
        invList.addObject(new ElementLabel(new Vector2(), index + "/" + inv.getList().size()));
    }
    
    @Override
    public void create() {
        Events.EVENTMANAGER.registerEventListener(this);

        container = new ContainerBlock(new Vector2(), new Vector2(312, 200));

        GUIAnchorSet set = new GUIAnchorSet();

        set.addAnchor(new AnchorCenterX());
        set.addAnchor(new AnchorBottom(0.12f));

        container.assignAnchorSet(set);
        ElementLabel invLabel;

        invLabel = new ElementLabel(new Vector2(), "Inventory:");

        invLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)).addAnchor(new AnchorTop(0)));

        container.addObject(invLabel);
        
        invList = new ContainerList(new Vector2(), new Vector2(500,200));
        invList.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)).addAnchor(new AnchorTop(0)));
        
        container.addObject(invList);
    }
}
