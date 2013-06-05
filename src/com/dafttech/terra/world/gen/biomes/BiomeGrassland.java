package com.dafttech.terra.world.gen.biomes;

import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.gen.WorldGenerator;
import com.dafttech.terra.world.subtiles.SubtileGrass;
import com.dafttech.terra.world.tiles.TileDirt;
import com.dafttech.terra.world.tiles.TileGrass;
import com.dafttech.terra.world.tiles.TileStone;

public class BiomeGrassland extends Biome {
    public BiomeGrassland(String name) {
        super(name);
    }

    public static Biome instance = new BiomeGrassland("Grassland");

    @Override
    public void generateTerrain(WorldGenerator gen) {
        for (int x = 0; x < gen.world.size.x; x++) {
            for (int y = 0; y < gen.world.size.y; y++) {
                gen.world.map[x][y] = y == gen.world.size.y - 1 ? new TileGrass(new Position(x, y), gen.world)
                        : y > gen.world.size.y - gen.world.size.y / 6 ? new TileDirt(new Position(x, y), gen.world)
                                .addSubtile(y == gen.world.size.y - 2 ? new SubtileGrass() : null) : new TileStone(new Position(x, y), gen.world);
            }
        }
    }

    @Override
    public void populate(WorldGenerator gen) {
        // TODO Auto-generated method stub

    }
}
