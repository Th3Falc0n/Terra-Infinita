package com.dafttech.terra.game.world.entities.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInventory;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources;

public abstract class Item extends Entity implements IDrawableInventory {
    public Item(Vector2 pos, World world, Vector2 size) {
        super(pos, world, size);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("error");
    }

    public int getMaxStackSize() {
        return 99;
    }

    @Override
    public void drawInventory(AbstractScreen screen, Vector2 position) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(float delta) {
        Vector2 p = getWorld().localPlayer.getPosition();
        Vector2 s = getWorld().localPlayer.getSize();

        Vector2 vp = new Vector2(p.addNew(s.x * Options.BLOCK_SIZE / 2, s.y * Options.BLOCK_SIZE / 2));
        vp.sub(position);

        if (vp.len() < 20) {
            getWorld().removeEntity(this);
        }

        if (vp.len() < 100) {
            addForce((Vector2) vp.nor().mul(14f / vp.len2()));
        }

        super.update(delta);
    }
}
