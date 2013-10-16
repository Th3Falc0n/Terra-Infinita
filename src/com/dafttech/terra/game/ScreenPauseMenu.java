package com.dafttech.terra.game;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.Tooltip;
import com.dafttech.terra.engine.gui.anchors.AnchorCenterX;
import com.dafttech.terra.engine.gui.anchors.AnchorRight;
import com.dafttech.terra.engine.gui.anchors.AnchorTop;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerOnscreen;
import com.dafttech.terra.engine.gui.elements.ElementButton;
import com.dafttech.terra.engine.passes.RenderingPass;
import com.dafttech.terra.game.world.World;

public class ScreenPauseMenu extends AbstractScreen {
    World localWorld;

    ContainerOnscreen guiContainerScreen;

    ElementButton exitButton;
    ElementButton resumeButton;

    public ScreenPauseMenu(World w) {
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

        resumeButton = new ElementButton(new Vector2(), "Resume") {
            @Override
            public void onClick(int button) {
                TerraInfinita.$.setScreen(TerraInfinita.$.screenIngame);
            }
        };

        GUIAnchorSet resumeButtonSet = new GUIAnchorSet();

        resumeButtonSet.addAnchor(new AnchorCenterX());
        resumeButtonSet.addAnchor(new AnchorTop(0.2f));

        resumeButton.assignAnchorSet(resumeButtonSet);

        guiContainerScreen.addObject(exitButton);
        guiContainerScreen.addObject(resumeButton);
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

        localWorld.draw(this, localWorld.localPlayer);

        guiContainerScreen.update(delta);
        RenderingPass.rpGUIContainer.applyPass(this, null, null, guiContainerScreen);
    }
}
