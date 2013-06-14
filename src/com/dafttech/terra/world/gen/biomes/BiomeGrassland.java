package com.dafttech.terra.world.gen.biomes;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.gen.WorldGenerator;
import com.dafttech.terra.world.subtiles.SubtileGrass;
import com.dafttech.terra.world.tiles.TileDirt;
import com.dafttech.terra.world.tiles.TileGrass;
import com.dafttech.terra.world.tiles.TileStone;
import com.dafttech.terra.world.tiles.TileWeed;

public class BiomeGrassland extends Biome {
    public BiomeGrassland(String name) {
        super(name);
    }

    public static Biome instance = new BiomeGrassland("Grassland");

    @Override
    public void generateTerrain(WorldGenerator gen) {
        for (int x = 0; x < gen.world.size.x; x++) {
            for (int y = 0; y < gen.world.size.y; y++) {
                new Position(x, y).setTile(gen.world, y == 0 ? TerraInfinita.rnd.nextInt(20) == 0 ? new TileWeed() : new TileGrass()
                        : y < gen.world.size.y / 6 ? new TileDirt().addSubtile(y == 1 ? new SubtileGrass() : null) : new TileStone());
            }
        }
    }

    @Override
    public void populate(WorldGenerator gen) {
        // TODO Auto-generated method stub

    }
}
