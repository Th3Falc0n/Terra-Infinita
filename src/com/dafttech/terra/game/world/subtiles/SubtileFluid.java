package com.dafttech.terra.game.world.subtiles;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.renderer.SubtileRenderer;
import com.dafttech.terra.engine.renderer.SubtileRendererFluid;
import com.dafttech.terra.game.world.Facing;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class SubtileFluid extends Subtile {
    private float maxHeight = 10, height = maxHeight;
    private float nextFlow = 0, flowDelay = 0.2f;

    public SubtileFluid(Tile t) {
        super(t);
        nextFlow = t.getWorld().time + flowDelay;
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
        float remainingHeight = getRemainingHeight();
        float remainingFluid = 0;
        if (height > remainingHeight) {
            remainingFluid = height - remainingHeight;
            height = remainingHeight;
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
        if (height == maxHeight) monitored = true;

        flow(world, Facing.TOP, delta);
        if (!flow(world, Facing.BOTTOM, delta * 10)) {
            if (TerraInfinita.rnd.nextBoolean()) {
                flow(world, Facing.LEFT, delta);
            } else {
                flow(world, Facing.RIGHT, delta);
            }
        }
        checkHeight();
        // if (monitored) System.out.println(height);
    }

    public boolean flow(World world, Facing direction, float amount) {
        SubtileFluid fluid = getFluid(world, direction);
        if (fluid == null) return false;
        float oldHeight = height;
        if (amount > height) amount = height;
        if (direction == Facing.TOP) {
            if (height > maxHeight) {
                if (amount > height - maxHeight) amount = height - maxHeight;
                addHeight(fluid.addHeight(amount) - amount);
            }
        } else if (direction == Facing.BOTTOM) {
            addHeight(fluid.addHeight(amount) - amount);
        } else if (direction == Facing.LEFT || direction == Facing.RIGHT) {
            if (fluid.getHeight() + amount >= height - amount) amount = (fluid.getHeight() + height) / 2 - fluid.getHeight();
            addHeight(fluid.addHeight(amount) - amount);
        }
        fluid.checkHeight();
        return height != oldHeight;
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

    public boolean isFluidAbove(World world) {
        Tile tile = world.getTile(this.tile.getPosition().add(Facing.TOP));
        if (tile.isWaterproof()) return false;
        return tile.hasSubtile(getClass(), false);
    }

    public abstract SubtileFluid getNewFluid(Tile tile);

    public float getMaxHeight() {
        return maxHeight;
    }
}
