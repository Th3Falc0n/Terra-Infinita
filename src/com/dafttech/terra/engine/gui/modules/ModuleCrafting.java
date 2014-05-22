package com.dafttech.terra.engine.gui.modules;

import javax.swing.text.DefaultStyledDocument.ElementBuffer;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.AnchorBottom;
import com.dafttech.terra.engine.gui.anchors.AnchorCenterX;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.engine.gui.elements.ElementButton;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.entities.living.Player;

public class ModuleCrafting extends GUIModule {
    ContainerBlock recipeList, researchList;
    ContainerBlock shown;
    
    Player player;
    
    public ModuleCrafting(Player p) {
        player = p;
    }

    @Override
    public void create() {
        Events.EVENTMANAGER.registerEventListener(this);

        container = new ContainerBlock(new Vector2(), new Vector2(312, 200));

        GUIAnchorSet set = new GUIAnchorSet();

        set.addAnchor(new AnchorCenterX());
        set.addAnchor(new AnchorBottom(0.12f));

        container.assignAnchorSet(set);
        
        ElementButton btnRecipe, btnResearch;
        
        btnRecipe = new ElementButton(new Vector2(), "Recipes") {
            @Override
            public void actionPerformed(int button) {
                shown = recipeList;
            }
        };
        
        btnResearch = new ElementButton(new Vector2(), "Research") {
            @Override
            public void actionPerformed(int button) {
                shown = researchList;
            }
        };
        
        btnRecipe.assignAnchorSet(new GUIAnchorSet());
    }

}
