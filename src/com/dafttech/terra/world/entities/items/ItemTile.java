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
        super(tile.getPosition().toEntityPos(), tile.getWorld());
        
        wrappedTile = tile;
        
        setSize(BLOCK_SIZE, BLOCK_SIZE);

        getPosition().add(TerraInfinita.rnd.nextFloat() * 16, TerraInfinita.rnd.nextFloat() * 16);
        setVelocity(new Vector2(TerraInfinita.rnd.nextFloat() - 0.5f * 20, TerraInfinita.rnd.nextFloat() - 0.5f * 20));
    }
    
    @Override
    public TextureRegion getImage() {
        return wrappedTile.getImage();
    }
}
