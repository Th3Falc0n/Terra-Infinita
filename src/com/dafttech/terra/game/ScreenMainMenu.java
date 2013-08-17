package com.dafttech.terra.game;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.Tooltip;
import com.dafttech.terra.engine.gui.anchors.AnchorRight;
import com.dafttech.terra.engine.gui.anchors.AnchorTop;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerOnscreen;
import com.dafttech.terra.engine.gui.containers.ContainerWindow;
import com.dafttech.terra.engine.gui.elements.ElementButton;
import com.dafttech.terra.engine.passes.RenderingPass;
import com.dafttech.terra.game.world.World;

public class ScreenMainMenu extends AbstractScreen {
    ContainerOnscreen guiContainerScreen;

    ElementButton exitButton;

    public ScreenMainMenu() {
        guiContainerScreen = new ContainerOnscreen();

        exitButton = new ElementButton(new Vector2(0, 0), "Exit") {
            @Override
            public void onClick(int button) {
                Gdx.app.exit();
            }
        };

        GUIAnchorSet exitButtonSet = new GUIAnchorSet();

        exitButtonSet.addAnchor(new AnchorRight(0.01f));
        exitButtonSet.addAnchor(new AnchorTop(0.01f));

        exitButton.assignAnchorSet(exitButtonSet);
        exitButton.setTooltip("Close the game");

        guiContainerScreen.addObject(exitButton);
        guiContainerScreen.addObject(Tooltip.getLabel());


        guiContainerScreen.applyAllAssignedAnchorSets();
    }
    
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        
        guiContainerScreen.update(delta);
        RenderingPass.rpGUIContainer.applyPass(this, null, null, guiContainerScreen);
    }
}
