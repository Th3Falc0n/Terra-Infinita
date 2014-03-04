package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.input.InputHandler;
import com.dafttech.terra.game.world.Facing;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class SubtileWater extends SubtileFluid {
    float img = 0;
    boolean wavephase = false;

    public SubtileWater() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("wateranim", (int) img);
    }

    @Override
    public SubtileFluid getNewFluid() {
        return new SubtileWater();
    }

    @Override
    public void onTick(World world, float delta) {
        img += delta;
        if ((int) img > 3) img = 0;

        SubtileFluid fluid = null;
        if (InputHandler.$.isKeyDown("WAVESLEFT")) {
            fluid = getFluid(world, Facing.LEFT);
        } else if (InputHandler.$.isKeyDown("WAVESRIGHT")) {
            fluid = getFluid(world, Facing.RIGHT);
        }
        if (fluid != null) {
            float amount = 3;
            if (amount > pressure) amount = pressure;
            if (fluid.pressure + amount < fluid.maxPressure * 2) {
                addPressure(-amount);
                fluid.addPressure(amount);
                super.onTick(world, delta);
            }
        } else {
            super.onTick(world, delta);
        }
    }

    @Override
    public float getViscosity() {
        return (InputHandler.$.isKeyDown("WAVESRIGHT") || InputHandler.$.isKeyDown("WAVESLEFT")) ? 4 : 0;
    }

    @Override
    public int getMaxReach() {
        return (InputHandler.$.isKeyDown("WAVESRIGHT") || InputHandler.$.isKeyDown("WAVESLEFT")) ? 4 : 10;
    }

    @Override
    public int getPressCap() {
        return 4;
    }

    @Override
    public boolean providesSunlightFilter() {
        return true;
    }

    @Override
    public Color getFilterColor() {
        return Color.BLUE;
    }
}
