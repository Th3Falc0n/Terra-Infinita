package com.dafttech.terra.engine.gui.modules;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.*;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.engine.gui.elements.ElementButton;
import com.dafttech.terra.engine.gui.elements.ElementLabel;
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

        container.assignAnchorSet(set);

        ElementButton btnRecipe, btnResearch;

        btnRecipe = new ElementButton(new Vector2(), "Recipes") {
            @Override
            public void actionPerformed(int button) {
                shown.clearObjects();
                shown.addObject(recipeList);
            }
        };

        btnResearch = new ElementButton(new Vector2(), "Research") {
            @Override
            public void actionPerformed(int button) {
                shown.clearObjects();
                shown.addObject(researchList);
            }
        };

        btnRecipe.assignAnchorSet(new GUIAnchorSet(new AnchorLeft(0), new AnchorTop(0)));
        btnResearch.assignAnchorSet(new GUIAnchorSet(new AnchorRightNextTo(btnRecipe, 10)));

        container.addObject(btnRecipe);
        container.addObject(btnResearch);

        shown = new ContainerBlock(new Vector2(0, 20), new Vector2(312, 150));
        recipeList = new ContainerBlock(new Vector2(0, 0), new Vector2(312, 150));
        researchList = new ContainerBlock(new Vector2(0, 0), new Vector2(312, 150));

        shown.addObject(researchList);
        container.addObject(shown);

        recipeList.addObject(new ElementLabel(new Vector2(0, 0), "Learned Recipes:"));
        researchList.addObject(new ElementLabel(new Vector2(0, 0), "Research new Recipes:"));
    }

}
