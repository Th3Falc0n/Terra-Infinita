package com.dafttech.terra.game;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

public class ScreenIngame extends AbstractScreen {
    World localWorld = new World(new Vector2(1000, 500));

    @Override
    public void render(float delta) {
        delta *= BLOCK_SIZE;
        
        super.render(delta);

        localWorld.update(localWorld.localPlayer, delta);
        localWorld.draw(this, localWorld.localPlayer);
    }
}
