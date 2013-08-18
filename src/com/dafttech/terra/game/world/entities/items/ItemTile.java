package com.dafttech.terra.game.world.entities.items;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.tiles.Tile;

public class ItemTile extends Item {
    Tile wrappedTile;

    public ItemTile(Tile tile) {
        super(tile.position.toEntityPos(), tile.getWorld(), new Vector2(0.5f, 0.5f));

        wrappedTile = tile;

        getPosition().add(BLOCK_SIZE / 4, BLOCK_SIZE / 4);
        setVelocity(new Vector2((TerraInfinita.rnd.nextFloat() - 0.5f) * 80, (TerraInfinita.rnd.nextFloat() - 0.5f) * 80));

        if (wrappedTile.isLightEmitter()) {
            wrappedTile.getEmittedLight().setSize(wrappedTile.getEmittedLight().getSize() / 2);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (wrappedTile.isLightEmitter()) {
            wrappedTile.getEmittedLight().setPosition(position);
        }
    }

    @Override
    public TextureRegion getImage() {
        return wrappedTile.getImage();
    }

    @Override
    public boolean isLightEmitter() {
        return wrappedTile.isLightEmitter();
    }

    @Override
    public PointLight getEmittedLight() {
        return wrappedTile.getEmittedLight();
    }
}