package com.dafttech.terra.game.world.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class TileLog extends Tile {
    private boolean living = false, grown = false;
    private int height = 0, width = 0;
    private int maxHeight = 10, maxWidth = 5;

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("log");
    }

    @Override
    public void onTick(World world) {
        super.onTick(world);
        if (living) growTree(world);
    }

    public void growTree(World world) {
        Random rnd = new Random();
        if (!grown) {
            grown = true;
            if (height <= maxHeight && (width == 0 || rnd.nextInt(5) == 0) && world.getTile(getPosition().addY(-1)).isReplacable())
                world.setTile(
                        getPosition().addY(-1),
                        (height > maxHeight - getSmallLayerSize() ? getLeaf() : getLog()).setLiving(living).setSize(height + 1, width, maxHeight,
                                maxWidth), true);
            if (height > maxHeight / 2.5f) {
                if (rnd.nextInt((int) ((float) Math.abs(width) / maxWidth) + 1) == 0 && world.getTile(getPosition().addX(1)).isReplacable())
                    world.setTile(getPosition().addX(1),
                            (rnd.nextBoolean() ? getLog() : getLeaf()).setLiving(living).setSize(height + 1, width + 1, maxHeight, maxWidth), true);
                if (rnd.nextInt((int) ((float) Math.abs(width) / maxWidth) + 1) == 0 && world.getTile(getPosition().addX(-1)).isReplacable()) {
                    if (world.getTile(getPosition().addX(-1)).isReplacable())
                        world.setTile(getPosition().addX(-1), (height > maxHeight / 2 && rnd.nextBoolean() ? getLog() : getLeaf()).setLiving(living)
                                .setSize(height + 1, width - 1, maxHeight, maxWidth), true);
                }
            }
        }
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

}
