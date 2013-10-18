package com.dafttech.terra.game.world.subtiles;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.game.world.tiles.TileGrass;
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
        if (!world.getTile(tile.getPosition().addY(-1)).isAir() && world.getTile(tile.getPosition().addY(-1)).getClass() != TileGrass.class) {
            tile.removeSubtile(this);
        } else {
            spread(world);
        }
    }

    public void spread(World world) {
        Vector2i spreadPosition = tile.getPosition().add(new Random().nextInt(spreadDistance * 2) - spreadDistance,
                new Random().nextInt(spreadDistance * 2) - spreadDistance);
        Tile spreadTile = tile.world.getTile(spreadPosition);
        if (spreadTile != null && spreadTile instanceof TileDirt && tile.world.getTile(spreadPosition.addY(-1)).isAir()) {
            if (!spreadTile.hasSubtile(SubtileGrass.class, true)) {
                spreadTile.addSubtile(new SubtileGrass());
            }
        }
    }
}
