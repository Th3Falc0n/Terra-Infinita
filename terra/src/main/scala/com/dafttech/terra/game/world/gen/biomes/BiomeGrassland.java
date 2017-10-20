package com.dafttech.terra.game.world.gen.biomes;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2i;
import com.dafttech.terra.game.world.Chunk;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.gen.calc.PerlinNoise;
import com.dafttech.terra.game.world.subtiles.SubtileBone;
import com.dafttech.terra.game.world.subtiles.SubtileGrass;
import com.dafttech.terra.game.world.tiles.*;

public class BiomeGrassland extends Biome {
    public BiomeGrassland(String name) {
        super(name);
    }

    public static Biome instance = new BiomeGrassland("Grassland");

    @Override
    public void generateChunk(WorldGenerator gen, Chunk chunk) {
        PerlinNoise noise = gen.getNoise();

        Vector2i chunkPos = Vector2i.Null().getBlockInWorldPos(chunk);

        for (int x = chunkPos.x(); x < chunkPos.x() + chunk.world().chunksize().x(); x++) {
            int h = (int) ((1f + noise.perlinNoise(x / 150f)) * 75);

            for (int y = chunkPos.y() + chunk.world().chunksize().y() - 1; y >= chunkPos.y(); y--) {
                if (y <= h) break;

                Tile tile = null;

                if (y - 1 == h) {
                    tile = TerraInfinita.rnd().nextInt(40) == 0 ? new TileLog().setLiving(true) : new TileGrass();
                }

                if (tile == null) {
                    if (y < (gen.world.size().y() - h) / 5 + h) {
                        tile = new TileDirt();
                        if (y - 2 == h) {
                            tile.addSubtile(new SubtileGrass());
                        }
                    } else {
                        tile = new TileStone();
                        if (TerraInfinita.rnd().nextDouble() < 0.004) {
                            tile.addSubtile(new SubtileBone());
                        }
                    }
                }

                gen.world.setTile(x, y, tile, false);
            }
        }
    }

    @Override
    public void populateChunk(WorldGenerator gen, Chunk chunk) {
    }
}