package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.input.InputHandler$;
import com.dafttech.terra.game.world.Facing;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources$;

public class SubtileWater extends SubtileFluid {
    float img = 0;
    boolean wavephase = false;

    public SubtileWater() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("wateranim", (int) img);
    }

    @Override
    public SubtileFluid getNewFluid() {
        return new SubtileWater();
    }

    @Override
    public void onTick(World world, float delta) {
        img += delta;
        if ((int) img > 3) img = 0;

        float windSpeed = world.weather().getWindSpeed(world);
        Facing facing = windSpeed > 0 ? Facing.RIGHT$.MODULE$ : windSpeed < 0 ? Facing.LEFT$.MODULE$ : null;
        if (InputHandler$.MODULE$.isKeyDown("WAVESLEFT")) {
            facing = Facing.LEFT$.MODULE$;
            windSpeed = 5;
        } else if (InputHandler$.MODULE$.isKeyDown("WAVESRIGHT")) {
            facing = Facing.RIGHT$.MODULE$;
            windSpeed = 5;
        }
        if (facing != null && !wavephase) {
            wavephase = true;
            SubtileFluid fluid = getFluid(world, facing);
            int maxReach = 5;
            float amount = maxPressure * delta * Math.abs(windSpeed);
            while (maxReach > 0 && fluid != null && pressure > amount) {
                maxReach--;
                if (fluid.pressure + amount < fluid.maxPressure * 2) {
                    addPressure(-amount);
                    fluid.addPressure(amount);
                }
                fluid = fluid.getFluid(world, facing);
            }
        } else {
            wavephase = false;
            super.onTick(world, delta);
        }
    }

    @Override
    public float getViscosity() {
        return (InputHandler$.MODULE$.isKeyDown("WAVESRIGHT") || InputHandler$.MODULE$.isKeyDown("WAVESLEFT")) ? 4 : 0;
    }

    @Override
    public int getMaxReach() {
        return (InputHandler$.MODULE$.isKeyDown("WAVESRIGHT") || InputHandler$.MODULE$.isKeyDown("WAVESLEFT")) ? 4 : 10;
    }

    @Override
    public int getPressCap() {
        return 2;
    }

    @Override
    public boolean providesSunlightFilter() {
        return true;
    }

    @Override
    public Color getFilterColor() {
        return new Color(1 - pressure / maxPressure, 1 - pressure / maxPressure, 1, 1);
    }
}
