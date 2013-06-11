package com.dafttech.terra.game;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class ScreenIngame extends AbstractScreen {
    World localWorld;

    @Override
    public void show() {
        localWorld = new World(new Vector2(1000, 500));
        super.show();
    }
    
    @Override
    public void render(float delta) {
        delta *= BLOCK_SIZE / 2;

        super.render(delta);

        localWorld.update(localWorld.localPlayer, delta);

        localWorld.draw(this, localWorld.localPlayer);
    }
}
