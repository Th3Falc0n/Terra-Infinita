package com.dafttech.terra.game;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.gui.Tooltip;
import com.dafttech.terra.graphics.gui.anchors.AnchorCenterX;
import com.dafttech.terra.graphics.gui.anchors.AnchorRight;
import com.dafttech.terra.graphics.gui.anchors.AnchorTop;
import com.dafttech.terra.graphics.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.graphics.gui.containers.ContainerOnscreen;
import com.dafttech.terra.graphics.gui.containers.ContainerWindow;
import com.dafttech.terra.graphics.gui.elements.ElementButton;
import com.dafttech.terra.graphics.gui.elements.ElementLabel;
import com.dafttech.terra.graphics.passes.RenderingPass;

public class ScreenPauseMenu extends AbstractScreen {
    World localWorld;
    
    ContainerOnscreen guiContainerScreen;

    ContainerWindow testWindow;

    ElementButton exitButton;
    ElementButton resumeButton;
    
    public ScreenPauseMenu(World w) {
        localWorld = w;

        guiContainerScreen = new ContainerOnscreen(new Vector2(0, 0), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

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

        guiContainerScreen.addElement(exitButton);
        
        
        
        resumeButton = new ElementButton(new Vector2(0, 0), "Resume") {
            @Override
            public void onClick(int button) {
                TerraInfinita.$.setScreen(TerraInfinita.$.screenIngame);
            }
        };
        
        GUIAnchorSet resumeButtonSet = new GUIAnchorSet();

        resumeButtonSet.addAnchor(new AnchorCenterX());
        resumeButtonSet.addAnchor(new AnchorTop(0.2f));

        resumeButton.assignAnchorSet(resumeButtonSet);

        
        
        guiContainerScreen.addElement(exitButton);
        guiContainerScreen.addElement(resumeButton);
        guiContainerScreen.addElement(Tooltip.getLabel());

        testWindow = new ContainerWindow(new Vector2(0, 0), new Vector2(300, 300));
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
