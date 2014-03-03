package com.dafttech.terra.game.world.subtiles;

import java.util.Random;

import com.dafttech.terra.engine.renderer.SubtileRenderer;
import com.dafttech.terra.engine.renderer.SubtileRendererFluid;
import com.dafttech.terra.game.world.Facing;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class SubtileFluid extends Subtile {
    public float maxPressure = 10;
    public float pressure = maxPressure;

    public SubtileFluid() {
        super();
    }

    @Override
    public void setTile(Tile t) {
        super.setTile(t);
        if (t != null) {
            if (isFluid(t.getWorld(), Facing.NONE)) {
                getFluid(t.getWorld(), Facing.NONE).addPressure(pressure);
                setPressure(0);
            }
        }
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        if (pressure < maxPressure / 1000) {
            tile.removeSubtile(this);
        } else {
            float viscosity = getViscosity();
            float amount = maxPressure / ((viscosity < 0 ? 0 : viscosity) + 1);
            float compSpeed = getCompSpeed();

            SubtileFluid fluid = getFluid(world, Facing.BOTTOM);
            if (fluid != null) {
                float total = pressure + fluid.pressure;
                float change = 0;
                if (total > maxPressure * (2 + compSpeed / maxPressure)) {
                    float avg = total / 2;
                    change = avg + compSpeed;
                } else {
                    float possAmount = total;
                    if (possAmount > maxPressure + (total - maxPressure) / maxPressure * compSpeed)
                        possAmount = maxPressure + (total - maxPressure) / maxPressure * compSpeed;
                    change = possAmount;
                }
                float possAmount = change - fluid.pressure;
                if (possAmount > amount) possAmount = amount;
                addPressure(-possAmount);
                fluid.addPressure(possAmount);
            }
            if (pressure > 0) {
                if (new Random().nextBoolean()) {
                    fluid = getFluid(world, Facing.RIGHT);
                    if (fluid != null) {
                        int reach = getMaxReach();
                        while (reach > 0 && new Random().nextInt(5) > 0 && fluid.isFluid(world, Facing.RIGHT)
                                && fluid.getFluid(world, Facing.RIGHT).pressure > fluid.maxPressure / 1000) {
                            reach--;
                            fluid = fluid.getFluid(world, Facing.RIGHT);
                        }
                    }
                } else {
                    fluid = getFluid(world, Facing.LEFT);
                    if (fluid != null) {
                        int reach = getMaxReach();
                        while (reach > 0 && new Random().nextInt(5) > 0 && fluid.isFluid(world, Facing.LEFT)
                                && fluid.getFluid(world, Facing.LEFT).pressure > fluid.maxPressure / 1000) {
                            reach--;
                            fluid = fluid.getFluid(world, Facing.LEFT);
                        }
                    }
                }
                if (fluid != null && fluid.pressure < pressure) {
                    float avg = (pressure + fluid.pressure) / 2;
                    float possAmount = avg - fluid.pressure;
                    if (possAmount > amount) possAmount = amount;
                    addPressure(-possAmount);
                    fluid.addPressure(possAmount);
                }
            }

            fluid = getFluid(world, Facing.TOP);
            if (fluid != null) {
                float total = pressure + fluid.pressure;
                if (pressure > maxPressure + (total - maxPressure) / maxPressure * compSpeed) {
                    float change = 0;
                    if (total > maxPressure * (2 + compSpeed / maxPressure)) {
                        float avg = total / 2;
                        change = avg + compSpeed;
                    } else {
                        float possAmount = total;
                        if (possAmount > maxPressure + (total - maxPressure) / maxPressure * compSpeed)
                            possAmount = maxPressure + (total - maxPressure) / maxPressure * compSpeed;
                        change = possAmount;
                    }
                    float possAmount = change - pressure;
                    if (possAmount > amount) possAmount = amount;
                    fluid.addPressure(-possAmount);
                    addPressure(possAmount);
                }
            }
        }
    }

    public void addPressure(float pressure) {
        this.pressure += pressure;
    }

    public SubtileFluid setPressure(float pressure) {
        this.pressure = pressure;
        return this;
    }

    @Override
    public SubtileRenderer getRenderer() {
        return SubtileRendererFluid.$Instance;
    }

    public SubtileFluid getFluid(World world, Facing direction) {
        Tile tile = world.getTile(this.tile.getPosition().add(direction));
        // if (tile.isWaterproof()) return null;
        SubtileFluid fluid = (SubtileFluid) tile.getSubtile(getClass(), false);
        if (fluid == null) {
            if (tile.isWaterproof()) return null;
            fluid = getNewFluid().setPressure(0);
            tile.addSubtile(fluid);
        }
        return fluid;
    }

    public boolean isFluid(World world, Facing direction) {
        Tile tile = world.getTile(this.tile.getPosition().add(direction));
        // if (tile.isWaterproof()) return false;
        return tile.hasSubtile(getClass(), false);
    }

    public abstract SubtileFluid getNewFluid();

    public abstract float getViscosity();

    public abstract int getMaxReach();

    public abstract int getCompSpeed();

    @Override
    public boolean isTileIndependent() {
        return true;
    }
}
