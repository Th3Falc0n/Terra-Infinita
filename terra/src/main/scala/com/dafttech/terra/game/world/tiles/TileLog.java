package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2i;
import com.dafttech.terra.engine.renderer.TileRenderer;
import com.dafttech.terra.engine.renderer.TileRendererMultiblock;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources$;

import java.util.Random;

public class TileLog extends Tile {
    private boolean living = false;
    private int height = 0, width = 0, maxHeight = 10, maxWidth = 10;
    private float grothDelay = 0.1f;

    public TileLog() {
        super();
        maxHeight = TerraInfinita.rnd().nextInt(40) + 5;
        maxWidth = TerraInfinita.rnd().nextInt(20) + 5;
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("log");
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        if (grothDelay <= 0) {
            growTree(world);
        } else {
            grothDelay -= delta;
        }
    }

    public void growTree(World world) {
        Random rnd = new Random();
        if (living) {
            if (height <= maxHeight && (width == 0 || rnd.nextInt(5) == 0) && world.getTile(getPosition().$minus(0, 1)).isReplacable())
                world.setTile(
                        getPosition().$minus(0, 1),
                        (height > maxHeight - getSmallLayerSize() ? getLeaf() : getLog()).setLiving(living).setSize(height + 1, width, maxHeight,
                                maxWidth), true);
            if (height > maxHeight / 2.5f) {
                if (rnd.nextInt((int) ((float) Math.abs(width) * (rnd.nextBoolean() ? 1 : height) / maxWidth) + 1) == 0
                        && world.getTile(getPosition().$plus(1, 0)).isReplacable())
                    world.setTile(getPosition().$plus(1, 0),
                            (rnd.nextBoolean() ? getLog() : getLeaf()).setLiving(living).setSize(height + 1, width + 1, maxHeight, maxWidth), true);
                if (rnd.nextInt((int) ((float) Math.abs(width) * (rnd.nextBoolean() ? 1 : height) / maxWidth) + 1) == 0
                        && world.getTile(getPosition().$minus(1, 0)).isReplacable()) {
                    if (world.getTile(getPosition().$minus(1, 0)).isReplacable())
                        world.setTile(getPosition().$minus(1, 0), (height > maxHeight / 2 && rnd.nextBoolean() ? getLog() : getLeaf()).setLiving(living)
                                .setSize(height + 1, width - 1, maxHeight, maxWidth), true);
                }
            }
        }
        living = false;
    }

    private TileLog setSize(int height, int width, int maxHeight, int maxWidth) {
        this.height = height;
        this.width = width;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        return this;
    }

    private int getSmallLayerSize() {
        return maxHeight / 5;
    }

    public TileLog getLog() {
        return new TileLog();
    }

    public TileLog getLeaf() {
        return new TileLeaf();
    }

    public TileLog setLiving(boolean living) {
        this.living = living;
        return this;
    }

    @Override
    public boolean isFlammable() {
        return true;
    }

    public boolean isFlatTo(World world, Vector2i pos) {
        return world.getTile(pos) instanceof TileLog || world.getTile(pos).isOpaque();
    }

    @Override
    public TileRenderer getRenderer() {
        return new TileRendererMultiblock(new Vector2i(4, 4));
    }
}
