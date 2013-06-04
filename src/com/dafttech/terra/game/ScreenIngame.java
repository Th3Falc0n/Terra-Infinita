package com.dafttech.terra.game;

import com.dafttech.terra.entity.Player;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class ScreenIngame extends AbstractScreen {
    Player localPlayer = new Player(new Vector2(0, 0));
    World localWorld = new World(5000, 3000);

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        super.render(delta);

        localWorld.drawWorld(this, localPlayer);
    }
}
