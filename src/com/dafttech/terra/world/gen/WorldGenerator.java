package com.dafttech.terra.world.gen;

import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.subtiles.SubtileGrass;
import com.dafttech.terra.world.tiles.TileDirt;
import com.dafttech.terra.world.tiles.TileStone;

public class WorldGenerator {
    World world = null;

    public WorldGenerator(World world) {
        this.world = world;
    }

    public void generate() {
        for (int x = 0; x < world.size.x; x++) {
            for (int y = 0; y < world.size.y; y++) {
                world.map[x][y] = y > world.size.y - world.size.y / 6 ? new TileDirt(new Position(x, y))
                        .addSubtile(y == world.size.y - 1 ? new SubtileGrass() : null) : new TileStone(new Position(x, y));
            }
        }
    }
}
