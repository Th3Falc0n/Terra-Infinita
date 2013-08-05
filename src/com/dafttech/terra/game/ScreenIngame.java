package com.dafttech.terra.game;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.gui.containers.ContainerOnscreen;
import com.dafttech.terra.graphics.gui.elements.ElementButton;
import com.dafttech.terra.graphics.gui.elements.ElementSlot;
import com.dafttech.terra.graphics.passes.RenderingPass;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class ScreenIngame extends AbstractScreen {
    World localWorld;
    InputHandler inputHandler;

    ContainerOnscreen guiContainerScreen;

    @Override
    public void show() {
        super.show();

        localWorld = new World(new Vector2(1000, 500));

        InputHandler.init();

        guiContainerScreen = new ContainerOnscreen(new Vector2(0, 0), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        guiContainerScreen.addElement(new ElementButton(new Vector2(0, 0), "Exit") {
            @Override
            public void onClick(int button) {
                Gdx.app.exit();
            }
        });
        
        guiContainerScreen.addElement(new ElementSlot(new Vector2(500, 200)));

        super.show();
    }

    public World getWorld() {
        return localWorld;
    }

    @Override
    public void render(float delta) {
        delta *= BLOCK_SIZE / 2;

        super.render(delta);

        InputHandler.update();
        localWorld.update(delta);
        localWorld.draw(this, localWorld.localPlayer);

        RenderingPass.rpGUIContainer.applyPass(this, null, localWorld, guiContainerScreen);
    }
}
