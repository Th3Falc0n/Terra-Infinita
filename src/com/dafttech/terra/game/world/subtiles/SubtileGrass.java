package com.dafttech.terra.game.world.subtiles;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.resources.Resources;

public class SubtileGrass extends Subtile {
    public SubtileGrass() {
        super(null);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("mask_grass");
    }

    int spreadDistance = 3;

    @Override
    public void onTick(World world) {
        int spreadX = tile.getPosition().x + new Random().nextInt(spreadDistance * 2) - spreadDistance;
        int spreadY = tile.getPosition().y + new Random().nextInt(spreadDistance * 2) - spreadDistance;
        Tile spreadTile = tile.world.getTile(spreadX, spreadY);
        if (spreadTile != null && spreadTile instanceof TileDirt && tile.world.getTile(spreadX, spreadY - 1).isAir()) {
            if (!spreadTile.hasSubtile(SubtileGrass.class, true)) {
                spreadTile.addSubtile(new SubtileGrass());
            }
        }
    }
}
