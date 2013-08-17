package com.dafttech.terra.game.world.entities.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInventory;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
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
        super.update(delta);
    }
}
