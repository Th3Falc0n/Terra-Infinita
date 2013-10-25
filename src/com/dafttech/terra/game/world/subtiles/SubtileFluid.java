package com.dafttech.terra.game.world.subtiles;

import com.dafttech.terra.engine.renderer.SubtileRenderer;
import com.dafttech.terra.engine.renderer.SubtileRendererFluid;
import com.dafttech.terra.game.world.Facing;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class SubtileFluid extends Subtile {
    private float height = 10;

    public SubtileFluid(Tile t) {
        super(t);
    }

    public boolean isFluid(Facing facing) {
        return tile.getWorld().getTile(tile.getPosition().add(facing)).hasSubtile(getClass(), false);
    }

    public float getHeight() {
        return height;
    }

    @Override
    public SubtileRenderer getRenderer() {
        return SubtileRendererFluid.$Instance;
    }

    public SubtileFluid setHeight(float height) {
        this.height = height;
        return this;
    }

    public float addHeight(float height) {
        this.height += height;
        if (height > 10) {
            float remHeight = height - 10;
            height = 10;
            return remHeight;
        }
        return 0;
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        if (height == 0) {
            tile.removeSubtile(this);
        } else {
            Tile tileBelow = world.getTile(tile.getPosition().add(Facing.BOTTOM));
            if (!tileBelow.isWaterproof()) {
                Subtile subtile = tileBelow.getSubtile(getClass(), false);
                if (subtile == null) {
                    subtile = getNewFluid(tileBelow).setHeight(0);
                    tileBelow.addSubtile(subtile);

                }
                setHeight(((SubtileFluid) subtile).addHeight(height));
            }
        }
    }

    public abstract SubtileFluid getNewFluid(Tile tile);
}
