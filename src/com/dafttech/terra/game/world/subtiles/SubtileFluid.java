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
    private float frozen = 0;

    public SubtileFluid(Tile t) {
        super(t);
    }

    @Override
    public void setTile(Tile t) {
        if (isFluid(t.getWorld(), Facing.NONE)) {
            getFluid(t.getWorld(), Facing.NONE).addPressure(pressure);
            setPressure(0);
        }
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        if (pressure < maxPressure / 1000) {
            tile.removeSubtile(this);
        } else {
            if (frozen <= 0) {
                float viscosity = getViscosity();
                if (viscosity < 0) viscosity = 0;
                float amount = maxPressure / (viscosity + 1);
                float overflow = 0;
                SubtileFluid fluid = getFluid(world, Facing.BOTTOM);
                if (fluid != null) {
                    float possAmount = pressure;
                    if (possAmount > amount) possAmount = amount;
                    addPressure(-possAmount);
                    overflow = fluid.addPressure(possAmount);
                    amount += -possAmount + overflow;
                    overflow = addPressure(overflow);
                }
                if (pressure > 0 && amount > 0) {
                    if (new Random().nextBoolean()) {
                        fluid = getFluid(world, Facing.RIGHT);
                    } else {
                        fluid = getFluid(world, Facing.LEFT);
                    }
                    if (fluid != null && fluid.pressure < pressure) {
                        float avg = (pressure + fluid.pressure) / 2;
                        float possAmount = avg - fluid.pressure;
                        if (possAmount > amount) possAmount = amount;
                        addPressure(-possAmount);
                        overflow = fluid.addPressure(possAmount + overflow);
                        overflow = addPressure(overflow);
                    }
                }
                if (overflow > 0) {
                    fluid = getFluid(world, Facing.TOP);
                    if (fluid != null) overflow = fluid.addPressure(overflow);
                    pressure += overflow;
                }
            } else {
                frozen -= delta;
            }
        }
    }

    public float addPressure(float pressure) {
        this.pressure += pressure;
        float remaining = this.pressure - maxPressure;
        if (remaining > 0) {
            this.pressure -= remaining;
            return remaining;
        }
        return 0;
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
        if (tile.isWaterproof()) return null;
        SubtileFluid fluid = (SubtileFluid) tile.getSubtile(getClass(), false);
        if (fluid == null) {
            fluid = getNewFluid(tile).setPressure(0);
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

    public abstract float getViscosity();
}
