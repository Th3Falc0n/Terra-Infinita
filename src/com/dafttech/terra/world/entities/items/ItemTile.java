package com.dafttech.terra.world.entities.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entities.Item;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

public class ItemTile extends Item {
    Tile wrappedTile;

    public ItemTile(Tile tile) {
        super(tile.position.toEntityPos(), tile.getWorld());

        wrappedTile = tile;

        setSize(0.5f, 0.5f);

        getPosition().add(BLOCK_SIZE / 4, BLOCK_SIZE / 4);
        setVelocity(new Vector2((TerraInfinita.rnd.nextFloat() - 0.5f) * 80, (TerraInfinita.rnd.nextFloat() - 0.5f) * 80));
    }

    @Override
    public TextureRegion getImage() {
        return wrappedTile.getImage();
    }
}
