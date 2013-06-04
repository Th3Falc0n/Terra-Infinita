package com.dafttech.terra.game;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entity.Player;

public class ScreenIngame extends AbstractScreen {
    Player localPlayer = new Player(new Vector2(250, 250));
    World localWorld = new World(1000, 500);

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        super.render(delta);

        localWorld.draw(this, localPlayer);
    }
}
