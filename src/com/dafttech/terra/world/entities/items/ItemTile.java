package com.dafttech.terra.world.entities.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entities.Item;

public class ItemTile extends Item {
    Tile wrappedTile;

    public ItemTile(Tile tile) {
        super(tile.position.toEntityPos(), tile.getWorld());

        wrappedTile = tile;

        setSize(0.5f, 0.5f);

        getPosition().add(TerraInfinita.rnd.nextFloat() * 16, TerraInfinita.rnd.nextFloat() * 16);
        setVelocity(new Vector2(TerraInfinita.rnd.nextFloat() - 0.5f * 50, TerraInfinita.rnd.nextFloat() - 0.5f * 50));
    }

    @Override
    public TextureRegion getImage() {
        return wrappedTile.getImage();
    }
}
