package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.resources.Resources$;

import java.util.Random;

public class SubtileGrass extends Subtile {
    public SubtileGrass() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("mask_grass");
    }

    int spreadDistance = 3;

    @Override
    public void onTick(World world, float delta) {
        if (world.getTile(tile.getPosition().addY(-1)).isOpaque()) {
            tile.removeSubtile(this);
        } else if (world.getTile(tile.getPosition().addY(-1)).getTemperature() > 50 && !(this instanceof SubtileDryGrass)) {
            tile.removeSubtile(this);
            tile.addSubtile(new SubtileDryGrass());
        } else {
            spread(world);
        }
    }

    public void spread(World world) {
        Vector2i spreadPosition = tile.getPosition().add(new Random().nextInt(spreadDistance * 2) - spreadDistance,
                new Random().nextInt(spreadDistance * 2) - spreadDistance);
        Tile spreadTile = world.getTile(spreadPosition);
        if (spreadTile != null && spreadTile instanceof TileDirt && world.getTile(spreadPosition.addY(-1)).isAir()) {
            if (!spreadTile.hasSubtile(SubtileGrass.class, true)) {
                spreadTile.addSubtile(new SubtileGrass());
            }
        }
    }
}
