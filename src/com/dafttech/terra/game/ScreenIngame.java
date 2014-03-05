package com.dafttech.terra.game;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.MouseSlot;
import com.dafttech.terra.engine.gui.Tooltip;
import com.dafttech.terra.engine.gui.anchors.AnchorRight;
import com.dafttech.terra.engine.gui.anchors.AnchorTop;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerOnscreen;
import com.dafttech.terra.engine.gui.elements.ElementButton;
import com.dafttech.terra.engine.gui.modules.ModuleChat;
import com.dafttech.terra.engine.input.InputHandler;
import com.dafttech.terra.engine.passes.RenderingPass;
import com.dafttech.terra.game.world.World;

public class ScreenIngame extends AbstractScreen {
    World localWorld;
    InputHandler inputHandler;

    ContainerOnscreen guiContainerScreen;

    ElementButton exitButton;
    ModuleChat chat;

    public ScreenIngame(World w) {
        localWorld = w;

        guiContainerScreen = new ContainerOnscreen();

        exitButton = new ElementButton(new Vector2(), "Exit") {
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

        chat = new ModuleChat();
        chat.create();

        guiContainerScreen.addObject(chat.getContainer());
        guiContainerScreen.addObject(localWorld.localPlayer.hudBottom.getContainer());
        guiContainerScreen.addObject(localWorld.localPlayer.guiInventory.getContainer());
        guiContainerScreen.addObject(exitButton);
        guiContainerScreen.addObject(Tooltip.getLabel());
        guiContainerScreen.addObject(MouseSlot.getRenderSlot());

        guiContainerScreen.applyAllAssignedAnchorSets();
    }

    @Override
    public void show() {
        super.show();
    }

    public World getWorld() {
        return localWorld;
    }

    @Override
    public void render(float delta) {
        if (delta > 0.5f) {
            TerraInfinita.$.setScreen(TerraInfinita.$.screenPause);
            return;
        }

        super.render(delta);

        localWorld.update(null, delta);
        TimeKeeping.timeKeeping("screen after update");

        update(delta);
        TimeKeeping.timeKeeping("Screen update");

        localWorld.draw(null, null, this, localWorld.localPlayer);
        TimeKeeping.timeKeeping("screen after draw");

        guiContainerScreen.update(delta);
        TimeKeeping.timeKeeping("GUI update");

        RenderingPass.rpGUIContainer.applyPass(this, null, localWorld, guiContainerScreen);
        TimeKeeping.timeKeeping("GUI draw");
    }

    public void update(float delta) {
    }
}
