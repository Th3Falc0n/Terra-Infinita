package com.dafttech.terra.engine.gui.modules;

import com.dafttech.terra.engine.gui.containers.GUIContainer;

public abstract class GUIModule {
    public GUIContainer container;
    
    public GUIContainer getContainer() {
        return container;
    }
    
    public abstract void create();
    
    public void update(float delta) {
        
    }
}
