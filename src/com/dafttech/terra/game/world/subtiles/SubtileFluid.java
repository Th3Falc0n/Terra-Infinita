package com.dafttech.terra.game.world.subtiles;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.renderer.SubtileRenderer;
import com.dafttech.terra.engine.renderer.SubtileRendererFluid;
import com.dafttech.terra.game.world.Facing;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class SubtileFluid extends Subtile {
    private float maxHeight = 10, height = maxHeight;
    private boolean dead = false;

    public SubtileFluid(Tile t) {
        super(t);
    }

    @Override
    public void setTile(Tile t) {
        if (isFluid(t.getWorld(), Facing.NONE)) {
            getFluid(t.getWorld(), Facing.NONE).addHeight(height);
            dead = true;
        }
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
        float remainingHeight = getRemainingHeight();
        float remainingFluid = 0;
        if (height > remainingHeight) {
            remainingFluid = height - remainingHeight;
            height = remainingHeight;
        } else if (this.height + height < 0) {
            remainingFluid = this.height + height;
            height = -this.height;
        }
        this.height += height;
        return remainingFluid;
    }

    public float getRemainingHeight() {
        return maxHeight - height;
    }

    boolean monitored = false;

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        if (dead) {
            tile.removeSubtile(this);
        } else {
            flow(world, Facing.TOP, delta);
            float waterLeft = delta * flowSpeed();
            waterLeft = (waterLeft - flow(world, Facing.BOTTOM, waterLeft)) * 3;
            if (TerraInfinita.rnd.nextBoolean()) {
                waterLeft = waterLeft - flow(world, Facing.LEFT, waterLeft);
                if (!clearFloor()) flow(world, Facing.RIGHT, waterLeft);
            } else {
                waterLeft = waterLeft - flow(world, Facing.RIGHT, waterLeft);
                if (!clearFloor()) flow(world, Facing.LEFT, waterLeft);
            }
            checkHeight();
        }
    }

    public float flow(World world, Facing direction, float amount) {
        SubtileFluid fluid = getFluid(world, direction);
        if (fluid == null) return 0;
        float oldHeight = height;
        if (amount > height) amount = height;
        if (direction == Facing.TOP) {
            if (height > maxHeight) {
                if (amount > height - maxHeight) amount = height - maxHeight;
                addHeight(fluid.addHeight(amount) - amount);
            }
        } else if (direction == Facing.BOTTOM) {
            // addHeight(1 - fluid.addHeight(amount)); // create ocean
            addHeight(fluid.addHeight(amount) - amount);
        } else if (direction == Facing.LEFT || direction == Facing.RIGHT) {
            if (fluid.getHeight() + amount >= height - amount) amount = (fluid.getHeight() + height) / 2 - fluid.getHeight();
            addHeight(fluid.addHeight(amount) - amount);
            fluid.flow(world, Facing.BOTTOM, fluid.getMaxHeight());
        }
        fluid.checkHeight();
        return Math.abs(height - oldHeight);
    }

    public void checkHeight() {
        if (height == 0) tile.removeSubtile(this);
    }

    public SubtileFluid getFluid(World world, Facing direction) {
        Tile tile = world.getTile(this.tile.getPosition().add(direction));
        if (tile.isWaterproof()) return null;
        SubtileFluid fluid = (SubtileFluid) tile.getSubtile(getClass(), false);
        if (fluid == null) {
            fluid = getNewFluid(tile).setHeight(0);
            tile.addSubtile(fluid);
        }
        return fluid;
    }

    public boolean isFluid(World world, Facing direction) {
        Tile tile = world.getTile(this.tile.getPosition().add(direction));
        if (tile.isWaterproof()) return false;
        return tile.hasSubtile(getClass(), false);
    }

    public abstract SubtileFluid getNewFluid(Tile tile);

    public abstract float flowSpeed();

    public abstract boolean clearFloor();

    public float getMaxHeight() {
        return maxHeight;
    }
}
