package com.dafttech.terra.game.world.gen.biomes;

import com.dafttech.terra.game.world.Position;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.gen.calc.PerlinNoise;
import com.dafttech.terra.game.world.subtiles.SubtileGrass;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.game.world.tiles.TileGrass;

public class BiomeGrassland extends Biome {
    public BiomeGrassland(String name) {
        super(name);
    }

    public static Biome instance = new BiomeGrassland("Grassland");

    @Override
    public void generateTerrain(WorldGenerator gen) {
        PerlinNoise noise = gen.getNoise();

        for (int x = 0; x < gen.world.size.x; x++) {
            int h = (int) ((1f + noise.perlinNoise(x / 150f)) * 75);

            for (int y = (int) gen.world.size.y - 1; y > h; y--) {
                Tile tile = null;

                if (y - 1 == h) {
                    tile = new TileGrass();
                }

                if (tile == null) {
                    tile = new TileDirt();
                    if (y - 2 == h) {
                        tile.addSubtile(new SubtileGrass());
                    }
                }

                new Position(x, y).setTile(gen.world, tile);
            }
        }
    }

    @Override
    public void populate(WorldGenerator gen) {
        // TODO Auto-generated method stub

    }
}
