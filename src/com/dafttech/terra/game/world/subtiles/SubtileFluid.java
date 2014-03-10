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
        flow(world, delta);
    }

    @SuppressWarnings("unused")
    public void flow(World world, float delta) {
        if (pressure < maxPressure / 1000) {
            tile.removeSubtile(this);
        } else {
            float amount = maxPressure / ((getViscosity() < 0 ? 0 : getViscosity()) + 1) * delta * 60;
            float pressCap = getPressCap();
            float amountDown = flowDown(world, amount, pressCap);
            float amountSide = flowSide(world, amountDown, pressCap);
            float amountUp = flowUp(world, amount, pressCap);
        }
    }

    public float flowDown(World world, float amount, float pressCap) {
        SubtileFluid fluid = getFluid(world, Facing.BOTTOM);
        if (fluid != null) {
            float total = pressure + fluid.pressure;
            float change = 0;
            if (total > maxPressure * (2 + pressCap / maxPressure)) {
                float avg = total / 2;
                change = avg + pressCap;
            } else {
                float possAmount = total;
                if (possAmount > maxPressure + (total - maxPressure) / maxPressure * pressCap)
                    possAmount = maxPressure + (total - maxPressure) / maxPressure * pressCap;
                change = possAmount;
            }
            float possAmount = change - fluid.pressure;
            if (possAmount > amount) possAmount = amount;
            if ((possAmount > 0 && !fluid.tile.isWaterproof()) || (possAmount < 0 && !tile.isWaterproof())
                    || fluid.tile.isWaterproof() == tile.isWaterproof()) {
                addPressure(-possAmount);
                fluid.addPressure(possAmount);
                return amount - possAmount < 0 ? 0 : amount - possAmount;
            }
        }
        return amount;
    }

    public float flowSide(World world, float amount, float pressCap) {
        if (amount > 0) {
            Facing facing = new Random().nextBoolean() ? Facing.RIGHT : Facing.LEFT;
            SubtileFluid fluid = getFluid(world, facing);
            if (fluid != null) {
                int reach = getMaxReach();
                while (reach > 0 && new Random().nextInt(5) > 0 && fluid.isFluid(world, facing)
                        && fluid.getFluid(world, facing).tile.isWaterproof() == fluid.tile.isWaterproof()
                        && fluid.getFluid(world, facing).pressure > fluid.maxPressure / 20) {
                    reach--;
                    fluid = fluid.getFluid(world, facing);
                }
            }
            if (fluid != null && fluid.pressure < pressure) {
                float avg = (pressure + fluid.pressure) / 2;
                float possAmount = avg - fluid.pressure;
                if (possAmount > amount) possAmount = amount;
                if ((possAmount > 0 && !fluid.tile.isWaterproof()) || (possAmount < 0 && !tile.isWaterproof())
                        || fluid.tile.isWaterproof() == tile.isWaterproof()) {
                    addPressure(-possAmount);
                    fluid.addPressure(possAmount);
                    return amount - possAmount < 0 ? 0 : amount - possAmount;
                }
            }
        }
        return amount;
    }

    public float flowUp(World world, float amount, float pressCap) {
        SubtileFluid fluid = getFluid(world, Facing.TOP);
        if (fluid != null) {
            float total = pressure + fluid.pressure;
            if (pressure > maxPressure + (total - maxPressure) / maxPressure * pressCap) {
                float change = 0;
                if (total > maxPressure * (2 + pressCap / maxPressure)) {
                    float avg = total / 2;
                    change = avg + pressCap;
                } else {
                    float possAmount = total;
                    if (possAmount > maxPressure + (total - maxPressure) / maxPressure * pressCap)
                        possAmount = maxPressure + (total - maxPressure) / maxPressure * pressCap;
                    change = possAmount;
                }
                float possAmount = change - pressure;
                if (possAmount > amount) possAmount = amount;
                if ((possAmount > 0 && !tile.isWaterproof()) || (possAmount < 0 && !fluid.tile.isWaterproof())
                        || fluid.tile.isWaterproof() == tile.isWaterproof()) {
                    fluid.addPressure(-possAmount);
                    addPressure(possAmount);
                    return amount - possAmount < 0 ? 0 : amount - possAmount;
                }
            }
        }
        return amount;
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

    public abstract int getPressCap();

    @Override
    public boolean isTileIndependent() {
        return true;
    }
}
