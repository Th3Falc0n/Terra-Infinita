package com.dafttech.terra.engine.gui.modules;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.AnchorBottom;
import com.dafttech.terra.engine.gui.anchors.AnchorLeft;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.engine.gui.containers.ContainerList;
import com.dafttech.terra.engine.gui.elements.ElementLabel;

public class ModuleChat extends GUIModule {
    public ContainerList messageList = new ContainerList(new Vector2(10, 10), new Vector2(380, 230));
    
    @Override
    public void create() {
        container = new ContainerBlock(new Vector2(0, 0), new Vector2(400, 280));
        
        GUIAnchorSet set = new GUIAnchorSet();
        
        set.addAnchor(new AnchorLeft(0.01f));
        set.addAnchor(new AnchorBottom(0.01f));
        
        container.assignAnchorSet(set);

        container.addObject(messageList);
    }
    
    public void addMessage() {
        messageList.addObject(new ElementLabel(new Vector2(0, 0), "" + TerraInfinita.rnd.nextDouble()));
    }
}
