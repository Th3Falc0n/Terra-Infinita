package com.dafttech.terra.game.world.gen.biomes;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.Position;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.subtiles.SubtileGrass;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.game.world.tiles.TileGrass;
import com.dafttech.terra.game.world.tiles.TileStone;
import com.dafttech.terra.game.world.tiles.TileTorch;
import com.dafttech.terra.game.world.tiles.TileWeed;

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

                if (y == 0 && x % 16 == 0) {
                    new Position(x, y).setTile(gen.world, new TileTorch());
                }
            }
        }
    }

    @Override
    public void populate(WorldGenerator gen) {
        // TODO Auto-generated method stub

    }
}
